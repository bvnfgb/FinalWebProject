package edu.pnu.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pnu.domain.AwningControl;
import edu.pnu.domain.AwningControlStatus;
import edu.pnu.domain.ContractDeta;
import edu.pnu.domain.ManageArea;
import edu.pnu.jwt.persistence.MemberRepository;
import edu.pnu.persistence.AwningControlRepository;
import edu.pnu.persistence.AwningControlStatusRepository;
import edu.pnu.persistence.ContractDetaRepository;
import edu.pnu.persistence.ManageAreaRepository;
import edu.pnu.persistence.other.AwningIndividualStatus;
import edu.pnu.persistence.other.AwningUserDeviceView;
import edu.pnu.service.other.AddModify;
import edu.pnu.service.other.AwningStatResult;
@Service
@SuppressWarnings("rawtypes")

public class AwningServiceImpl implements AwningService {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	AwningControlRepository awningControlRepository;
	@Autowired
	ManageAreaRepository manageAreaRepository;
	@Autowired
	ContractDetaRepository contractDetaRepository;
	@Autowired
	AwningControlStatusRepository awningControlStatusRepository;
	
	//이하 구현서비스
	@Override
	public List getAwningList(String token) {// /user/map
		
		List list= awningControlRepository.findAllByUserMap();//리스트 찾아내기
		
		return list;
	}
	
	@Override
	@Transactional
	public int addAwning(String token, AwningControl awningControl,AddModify addModify) {
		// /admin/device/add , 특별한 반환값이 없기에 int로 결과를 알려준다.
		System.out.println(awningControl.toString()+"=awningControl");
		
		if(!checkAwningControlValid(awningControl))//true 유효값
			return 1;
		if(checkAwningControlBlank(awningControl))//true 비어있음
			return 1;
		
		List<ManageArea> list= manageAreaRepository.findByCity(awningControl.getManagementArea1());
		Integer manageArea1234 = null;
		if(list!=null) {
			for(ManageArea area:list) {
				if(area.getCity2()==null) {
					manageArea1234=area.getCityId(); 
					break;
				}
				if(area.getCity2().equals(awningControl.getManagementArea2())) {
					manageArea1234=area.getCityId();
					break;
				}
			}
		}
		
		else return 2;
		
		if(addModify==AddModify.MODIFY) {
			
			if(awningControl.getAwningId()==null)
				return 4;
			AwningControl findAwningControl=awningControlRepository.findByAwningId(awningControl.getAwningId());
			if(findAwningControl==null)
				return 4;
			
			
			}
				
		try {
			saveMethodForAddAwning(awningControl, manageArea1234);
		} catch (Exception e) {
			// TODO: handle exception
			return 3;
		}
		
			
		
		return 0;
	}
	@Override
	public AwningStatResult getAwningStat(String token, String deviceId) {
		// /user/device/view/{}, AwningStatResult는 반환값용 enum이다.
		if(isInvalidString(deviceId))
			return AwningStatResult.DEVICE_ID_NULL_OR_BLANK;
		AwningIndividualStatus awningIndividualStatus =awningControlRepository.findByIndvidualStatus(deviceId);
		//프로젝션 인터페이스 
		
		Optional<AwningControl> awningControlOptional=awningControlRepository.findById(deviceId);
		
		AwningControl awningControl=null;
		if(awningControlOptional.isPresent())
			awningControl=awningControlOptional.get();
		else
			return AwningStatResult.DEVICE_NOT_FOUND;
		
		ManageArea manageArea= manageAreaRepository.findById(awningControl.getManagementArea()).get();
		
		awningIndividualStatus.setManagementArea1(manageArea.getCity());
		awningIndividualStatus.
			setManagementArea2(manageArea.getCity2()!=null?manageArea.getCity2():null);
		
		AwningControlStatus awningControlStatus= awningControlStatusRepository.findById(deviceId).get();
		awnvlStng(awningIndividualStatus,awningControlStatus);
		
		
		return AwningStatResult.SUCCESS.withAwningIndividualStatus(awningIndividualStatus);
	}
	
	@Override
	public List getAwningLStatList(String token) {// /user/device/view
		List<AwningUserDeviceView> list=awningControlRepository.findAllByUserDevice();
		for(AwningUserDeviceView awningUserDeviceView:list) {
			
			ManageArea manageArea= manageAreaRepository.findById(awningUserDeviceView.getManagementArea()).get();
			awningUserDeviceView.setManagementArea1(manageArea.getCity());
			if(manageArea.getCity2()!=null)
				awningUserDeviceView.setManagementArea2(manageArea.getCity2());
		}
		return list;
	}
	
	@Override
	@Transactional
	public int deleteAwningSeleted(String token, List<String> list) {
		try {
			deleteAwningList(list);
		} catch (Exception e) {
			// TODO: handle exception
			return 1;
		}
		return 0;
	}
	
	
	
	//이하 메소드
	@Transactional
	private void deleteAwningList(List<String> list) {
		awningControlRepository.deleteAllById(list);
	}
	
	private void awnvlStng(AwningIndividualStatus awningIndividualStatus, AwningControlStatus awningControlStatus) {
		awningIndividualStatus.setAwningCondition(awningControlStatus.getAwningCondition());
		awningIndividualStatus.setBatteryCondition(awningControlStatus.getBatteryCondition());
		awningIndividualStatus.setLightingCondition(awningControlStatus.getLightingCondition());
		if(!isInvalidString(awningControlStatus.getAwningMessage()))
			awningIndividualStatus.setAwningMessage(awningControlStatus.getAwningMessage());
		if(!isInvalidString(awningControlStatus.getBatteryMessage()))
			awningIndividualStatus.setBatteryMessage(awningControlStatus.getBatteryMessage());
		if(!isInvalidString(awningControlStatus.getAwningMessage()))
			awningIndividualStatus.setLightingMessage(awningControlStatus.getLightingMessage());
	}

	@Transactional
	private void saveMethodForAddAwning(AwningControl awningControl,Integer manageArea1234) {
		
			awningControlRepository.save(
				AwningControl.builder().awningOpenTimeLeft(awningControl.getAwningOpenTimeLeft())
				.awningOpenTimeRight(awningControl.getAwningOpenTimeRight())
				.deviceId(awningControl.getDeviceId())
				.installationLocationMemo(awningControl.getInstallationLocationMemo())
				.managementArea(manageArea1234).latitude(awningControl.getLatitude()).longitude(awningControl.getLongitude())
				.managementNumber(awningControl.getManagementNumber())
				.windSpeedThreshold(awningControl.getWindSpeedThreshold())
				.awningReopenTimeMinutes(awningControl.getAwningReopenTimeMinutes())
				.controlId(awningControl.getControlId()).build()
				);
			contractDetaRepository.save(
				ContractDeta.builder().contractStartDate(awningControl.getStartDate())
				.contractTerminationDate(awningControl.getFinshDate())
				.registrationDate(new Date())
				.awningDeviceId(awningControl.getDeviceId())
				.build()
				);
			awningControlStatusRepository.save(AwningControlStatus.builder().awningDeviceId(awningControl.getDeviceId()).build());
		
		
	}
	
	private boolean checkAwningControlBlank(AwningControl awningControl) {
		
		if(awningControl.getAwningOpenTimeLeft().isBlank())
			return true;
		if(awningControl.getAwningOpenTimeRight().isBlank())
			return true;
		if(awningControl.getDeviceId().isBlank())
			return true;
		if(awningControl.getInstallationLocationMemo().isBlank())
			return true;
		if(awningControl.getWindSpeedThreshold().isBlank())
			return true;
		if(awningControl.getManagementArea1().isBlank())
			return true;
		
		return false;
	}

	private boolean checkAwningControlValid(AwningControl awningControl) {

		if(awningControl.getAwningReopenTimeMinutes()==null)
			return false;
		if(awningControl.getAwningOpenTimeLeft()==null)
			return false;
		if(awningControl.getAwningOpenTimeRight()==null)
			return false;
		if(awningControl.getControlId()==null)
			return false;
		if(awningControl.getDeviceId()==null)
			return false;
		if(awningControl.getInstallationLocationMemo()==null)
			return false;
		if(awningControl.getLatitude()==null)
			return false;
		if(awningControl.getLongitude()==null)
			return false;
		if(awningControl.getWindSpeedThreshold()==null)
			return false;
		if(awningControl.getManagementArea1()==null)
			return false;
		
		return true;
	}

	private boolean isInvalidString(String string) {
		if(string==null)
			return true;
		if(string.isEmpty()||string.isBlank())
			return true;
		return false;
		
	}

	

}
