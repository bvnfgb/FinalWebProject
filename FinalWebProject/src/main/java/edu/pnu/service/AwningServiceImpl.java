package edu.pnu.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import edu.pnu.persistence.other.AwningUserMap;
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
	public List<AwningUserMap> getAwningList(String token) {// /user/map
		
		List<AwningUserMap> list= awningControlRepository.findAllByUserMap();//리스트 찾아내기
		
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
		AwningControl findAwningControl=null;
		Integer contractId=null;
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
			findAwningControl=awningControlRepository.findByAwningId(awningControl.getAwningId());
			
			if(findAwningControl==null)
				return 4;
			try {
				contractId=contractDetaRepository.findByAwningDeviceId(findAwningControl.getDeviceId()).getContractId() ;
			} catch (Exception e) {
				// TODO: handle exception
				return 4;
			}
			
			
			}
			
		try {
			saveMethodForAddAwning(awningControl, manageArea1234,contractId);
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
		
		Optional<AwningControl> awningControlOptional=awningControlRepository.findByDeviceId(deviceId);
		
		AwningControl awningControl=null;
		if(awningControlOptional.isPresent())
			awningControl=awningControlOptional.get();
		else
			return AwningStatResult.DEVICE_NOT_FOUND;
		
		ManageArea manageArea= manageAreaRepository.findById(awningControl.getManagementArea()).get();
		
		awningIndividualStatus.setManagementArea1(manageArea.getCity());
		awningIndividualStatus.
			setManagementArea2(manageArea.getCity2()!=null?manageArea.getCity2():null);
		ContractDeta contractDeta=contractDetaRepository.findByAwningDeviceId(deviceId);
		if(contractDeta!=null) {
			awningIndividualStatus.setStartDate(contractDeta.getContractStartDate());
			awningIndividualStatus.setFinshDate(contractDeta.getContractTerminationDate());
		}
		
		
		AwningControlStatus awningControlStatus= awningControlStatusRepository.findById(deviceId).get();
		awnvlStng(awningIndividualStatus,awningControlStatus);
		
		
		return AwningStatResult.SUCCESS.withAwningIndividualStatus(awningIndividualStatus);
	}
	
	@Override
	public List getAwningLStatList(String token,HashMap<String,String> paramMap)  {// /user/device/view
		List<AwningUserDeviceView> list=awningControlRepository.findAllByUserDevice();
		String searchTerm=paramMap.get("searchTerm");
		String searchCriteria=paramMap.get("searchCriteria");
		String statusConnected=paramMap.get("statusConnected");
		String statusLighting=paramMap.get("statusLighting");
		String statusAwningExpand=paramMap.get("statusAwningExpand");
		String managementArea1=paramMap.get("managementArea1");
		String managementArea2=paramMap.get("managementArea2");
		String lightingCondition=paramMap.get("lightingCondition");
		String motorCondition=paramMap.get("motorCondition");
		String batteryCondition=paramMap.get("batteryCondition");
		final List<String>  keywordList=Arrays.asList("searchTerm","searchCriteria","statusConnected",
				"statusLighting","statusAwningExpand","managementArea1","managementArea2","lightingCondition",
				"motorCondition","batteryCondition");
		List<String> getKeywordList=new ArrayList<>();
		for(String keyword:keywordList) {
			getKeywordList.add(paramMap.get(keyword));
		}
		List<String> filterList=keywordList.stream().skip(2).toList();
		List<String> getFilterList=getKeywordList.stream().skip(2).toList();
		for(AwningUserDeviceView awningUserDeviceView:list) {
			
			ManageArea manageArea= manageAreaRepository.findById(awningUserDeviceView.getManagementArea()).get();
			awningUserDeviceView.setManagementArea1(manageArea.getCity());
			if(manageArea.getCity2()!=null)
				awningUserDeviceView.setManagementArea2(manageArea.getCity2());
			AwningControlStatus awningControlStatus=awningControlStatusRepository.findById(awningUserDeviceView.getDeviceId()).get();
			awningUserDeviceView.setMotorCondition(awningControlStatus.getMotorCondition());
			awningUserDeviceView.setLightingCondition(awningControlStatus.getLightingCondition());
			awningUserDeviceView.setBatteryCondition(awningControlStatus.getBatteryCondition());
		}
		
		System.out.println(searchTerm+"=searchTerm "+searchCriteria+"=searchCriteria");
		System.out.println(statusConnected+"=statusConnected "+statusLighting+"=statusLighting "+
				statusAwningExpand+"=statusAwningExpand "+managementArea1+"=managementArea1 "+
				managementArea2+"=managementArea2 "+lightingCondition+"=lightingCondition "+
				motorCondition+"=motorCondition "+batteryCondition+"=batteryCondition ");
		if(!isInvalidString(searchTerm)) {
			applySearch(list, searchTerm, searchCriteria);
		}
		if(useFilter(getFilterList)) {
			filterResult(list,filterList,getFilterList);
			
		}
			
		return list;
	}
	
	

	

	

	

	

	@Override
	@Transactional
	public int deleteAwningSeleted(String token, List<String> list) {
		System.out.println(list+"=list");
		try {
			
			int value= deleteAwningList(list);
			System.out.println("deleteAwningSeleted:"+value);
			return value;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		
		
	}
	
	
	
	//이하 메소드
	private void filterResult(List<AwningUserDeviceView> list,List<String> filterList,List<String> getFilterList) {
		for(int i=0;i<list.size();i++) {
			List<String> orgnlFllst=setFilterList(list.get(i));
			for(int j=0;j<orgnlFllst.size();j++) {
				if(!isInvalidString(getFilterList.get(j))) {
					if(!orgnlFllst.get(j).equals(getFilterList.get(j))&&!getFilterList.get(j).equals("full")) {
						list.remove(i--);
						break;
					}
				}
			}
		}
		
		
	}

	private List<String> setFilterList(AwningUserDeviceView view) {
		List<String> returnList=new ArrayList<>();
		returnList.add(view.getStatusConnected());returnList.add(view.getStatusLighting());
		returnList.add(view.getStatusAwningExpand());returnList.add(view.getManagementArea1());
		returnList.add(view.getManagementArea2());returnList.add(view.getLightingCondition());
		returnList.add(view.getMotorCondition());returnList.add(view.getBatteryCondition());
		return returnList;
	}
	private boolean useFilter(List<String> getFilterList) {
		for(String s:getFilterList)
			if(!isInvalidString(s))
				return true;
		return false;
	}
	private void applySearch(List<AwningUserDeviceView> list,String searchTerm,String searchCriteria) {
		for(int i=0;i<list.size();i++) {
			if(searchCriteria.equals("full")) {
				if(!list.get(i).getInstallationLocationMemo().contains(searchTerm)&&
						!list.get(i).getManagementNumber().contains(searchTerm)) 
				{
					
					list.remove(i--);//삭제시 인덱스 이동하기 때문에 정상화조치 
				}
			}
			else if(searchCriteria.equals("installationLocationMemo")) {
				if(!list.get(i).getInstallationLocationMemo().contains(searchTerm))
					list.remove(i--);
			}
			else {
				if(!list.get(i).getManagementNumber().contains(searchTerm))
					list.remove(i--);
			}
		}
		
	}
	@Transactional
	private int deleteAwningList(List<String> list) {
		return awningControlRepository.deleteAllByDeviceIdIn(list);
	}
	
	private void awnvlStng(AwningIndividualStatus awningIndividualStatus, AwningControlStatus awningControlStatus) {
		awningIndividualStatus.setMotorCondition(awningControlStatus.getMotorCondition());
		awningIndividualStatus.setBatteryCondition(awningControlStatus.getBatteryCondition());
		awningIndividualStatus.setLightingCondition(awningControlStatus.getLightingCondition());
		if(!isInvalidString(awningControlStatus.getMotorMessage()))
			awningIndividualStatus.setAwningMessage(awningControlStatus.getMotorMessage());
		if(!isInvalidString(awningControlStatus.getBatteryMessage()))
			awningIndividualStatus.setBatteryMessage(awningControlStatus.getBatteryMessage());
		if(!isInvalidString(awningControlStatus.getLightingMessage()))
			awningIndividualStatus.setLightingMessage(awningControlStatus.getLightingMessage());
	}

	@Transactional
	private void saveMethodForAddAwning(AwningControl awningControl,Integer manageArea1234,Integer contractId) {
			System.out.println("problem1");
			awningControl= AwningControl.builder().awningOpenTimeLeft(awningControl.getAwningOpenTimeLeft())
			.awningOpenTimeRight(awningControl.getAwningOpenTimeRight())
			.deviceId(awningControl.getDeviceId())
			.installationLocationMemo(awningControl.getInstallationLocationMemo())
			.managementArea(manageArea1234).latitude(awningControl.getLatitude()).longitude(awningControl.getLongitude())
			.managementNumber(awningControl.getManagementNumber())
			.windSpeedThreshold(awningControl.getWindSpeedThreshold())
			.awningReopenTimeMinutes(awningControl.getAwningReopenTimeMinutes())
			.controlId(awningControl.getControlId()).awningId(awningControl.getAwningId()!=null?awningControl.getAwningId():null)
			.startDate(awningControl.getStartDate()).finshDate(awningControl.getFinshDate()).build();
			awningControlRepository.save(
				awningControl
				);
			System.out.println("problem2");
			System.out.println("---contractId: "+contractId);
			Date startDate=awningControl.getStartDate();
			Date finshDate=awningControl.getFinshDate();
			startDate.setHours(0);
			finshDate.setHours(0);
			if(contractId!=null) {
				ContractDeta contractDeta=contractDetaRepository.findById(contractId).get();
				if(contractDeta.getContractStartDate().compareTo(awningControl.getStartDate())==0) {
					if(contractDeta.getContractTerminationDate().compareTo(awningControl.getFinshDate())==0);
					else
						contractId=null;
				}
				else
					contractId=null;	
			}
			System.out.println("contractId: "+contractId+"---");
			ContractDeta contractDeta=ContractDeta.builder().awningControl(awningControl)
					.contractStartDate(awningControl.getStartDate())
					.contractTerminationDate(awningControl.getFinshDate())
					.registrationDate(new Date())
					.build();
			
			if(contractId!=null) {
				contractDeta=ContractDeta.builder().awningControl(awningControl)
					.contractStartDate(awningControl.getStartDate())
					.contractTerminationDate(awningControl.getFinshDate())
					.registrationDate(new Date())
					.contractId(contractId)
					.build();
			}
			
			contractDetaRepository.save(
				contractDeta
				);
			System.out.println("problem3");
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
