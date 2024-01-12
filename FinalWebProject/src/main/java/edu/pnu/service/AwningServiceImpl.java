package edu.pnu.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pnu.domain.AwningDefaultOnly;
import edu.pnu.domain.AwningLocationOnly;
import edu.pnu.domain.AwningStatusOnly;
import edu.pnu.domain.ContractDeta;
import edu.pnu.domain.ContractDetaLog;
import edu.pnu.domain.ManageArea;
import edu.pnu.domain.other.AwningControl;
import edu.pnu.domain.other.AwningDeviceView;
import edu.pnu.domain.other.AwningUserMapDTO;
import edu.pnu.jwt.persistence.MemberRepository;
import edu.pnu.persistence.AwningDefaultRepository;
import edu.pnu.persistence.AwningLocationRepository;
import edu.pnu.persistence.AwningStatusRepository;
import edu.pnu.persistence.ContractDetaLogRepository;
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
	AwningDefaultRepository awningDefaultRepository;
	@Autowired
	AwningLocationRepository awningLocationRepository;
	@Autowired
	AwningStatusRepository awningStatusRepository;
	@Autowired
	ManageAreaRepository manageAreaRepository;
	@Autowired
	ContractDetaRepository contractDetaRepository;
	@Autowired
	ContractDetaLogRepository contractDetaLogRepository;
	
	//이하 구현서비스
	@Override
	public List<AwningUserMapDTO> getAwningList(String token) {// /user/map
		
		
		List<AwningLocationOnly> llist= awningLocationRepository.findAll();
		List<AwningStatusOnly> slist= awningStatusRepository.findAll();
		List<AwningDefaultOnly> dlist=awningDefaultRepository.findAll();
		
		List<AwningUserMapDTO> list=new ArrayList<>();
		mergeResult(list,llist,slist,dlist);
		
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
		Optional<AwningDefaultOnly> findAwningControl=null;
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
			findAwningControl=awningDefaultRepository.findById(awningControl.getAwningId());
			
			if(findAwningControl.isEmpty())
				return 4;
			
			
			
			}
			
		try {
			saveMethodForAddAwning(awningControl, manageArea1234,addModify);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 3;
		}
		
			
		
		return 0;
	}
	@Override
	public AwningStatResult getAwningStat(String token, String deviceId) {
		// /user/device/view/{}, AwningStatResult는 반환값용 enum이다.
		if(isInvalidString(deviceId))
			return AwningStatResult.DEVICE_ID_NULL_OR_BLANK;
		
		
		AwningDeviceView awningDeviceView=new AwningDeviceView();
		
		AwningDefaultOnly awningDefaultOnly= awningDefaultRepository.findByDeviceId(deviceId);
		AwningLocationOnly awningLocationOnly= awningLocationRepository.findByDeviceId(deviceId);
		AwningStatusOnly awningStatusOnly= awningStatusRepository.findByDeviceId(deviceId);
		ContractDeta contractDeta=contractDetaRepository.findByAwningDeviceId(deviceId);
		
		if(awningDefaultOnly==null)
			return AwningStatResult.DEVICE_NOT_FOUND;
		setAwningDeviceView(awningDefaultOnly,awningLocationOnly,awningStatusOnly,
				contractDeta,awningDeviceView);
		
		
		
		return AwningStatResult.SUCCESS.withAwningIndividualStatus(awningDeviceView);
	}
	
	



	@Override
	public List getAwningLStatList(String token,HashMap<String,String> paramMap)  {// /user/device/view
		List<AwningDefaultOnly> dlist=awningDefaultRepository.findAll();
		List<AwningLocationOnly> llist=awningLocationRepository.findAll();
		List<AwningStatusOnly> slist=awningStatusRepository.findAll();
		List<ContractDeta> clist=contractDetaRepository.findAll();
		List<AwningDeviceView> list=new ArrayList<>();
		
		
		
		for (int i = 0; i < dlist.size(); i++) {
		    AwningDefaultOnly defaultOnly = dlist.get(i);
		    AwningLocationOnly locationOnly = llist.get(i);
		    AwningStatusOnly statusOnly = slist.get(i);
		    ContractDeta contractDeta=clist.get(i);
		    AwningDeviceView awningDeviceView = new AwningDeviceView();
		    setAwningDeviceView(defaultOnly, locationOnly, statusOnly, contractDeta, awningDeviceView);

		    list.add(awningDeviceView);
		}
		
		
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
	
	@Override
	public void addAwningLog(String token, HashMap<String, String> unknownObject) {
		// TODO Auto-generated method stub
		unknownObject.get("deviceId");
	}
	
	//이하 메소드
	private void setAwningDeviceView(AwningDefaultOnly awningDefaultOnly, AwningLocationOnly awningLocationOnly,
			AwningStatusOnly awningStatusOnly,  ContractDeta contractDeta,
			AwningDeviceView awningDeviceView) {
		// TODO Auto-generated method stub
		awningDeviceView.setAwningId(awningDefaultOnly.getAwningId());
		awningDeviceView.setAwningMessage(awningStatusOnly.getAwningMessage());
		awningDeviceView.setAwningOpenScheduleTime(awningDefaultOnly.getAwningOpenScheduleTime());
		awningDeviceView.setAwningOpenTimeLeft(awningDefaultOnly.getAwningOpenTimeLeft());
		awningDeviceView.setAwningOpenTimeRight(awningDefaultOnly.getAwningOpenTimeRight());
		awningDeviceView.setAwningReopenTimeMinutes(awningDefaultOnly.getAwningReopenTimeMinutes());
		awningDeviceView.setBatteryCondition(awningStatusOnly.getBatteryCondition());
		awningDeviceView.setBatteryMessage(awningStatusOnly.getBatteryMessage());
		awningDeviceView.setControlId(awningDefaultOnly.getControlId());
		awningDeviceView.setDeviceId(awningDefaultOnly.getDeviceId());
		awningDeviceView.setInstallationLocationMemo(awningLocationOnly.getInstallationLocationMemo());
		awningDeviceView.setLastReportedDate(awningStatusOnly.getLastReportedDate());
		awningDeviceView.setLatitude(awningLocationOnly.getLatitude());
		awningDeviceView.setLongitude(awningLocationOnly.getLongitude());
		Integer mamagerMentArea=awningLocationOnly.getManagementArea();
		Optional<ManageArea> area=manageAreaRepository.findById(mamagerMentArea);
		awningDeviceView.setManagementArea1(area.get().getCity());
		awningDeviceView.setManagementArea2(area.get().getCity2()==null?null:area.get().getCity2());
		awningDeviceView.setManagementNumber(awningLocationOnly.getManagementNumber());
		awningDeviceView.setMotorCondition(awningStatusOnly.getMotorCondition());
		awningDeviceView.setStatusAwningExpand(awningStatusOnly.getStatusAwningExpand());
		awningDeviceView.setStatusBatteryCharge(awningStatusOnly.getStatusBatteryCharge());
		awningDeviceView.setStatusConnected(awningStatusOnly.getStatusConnected());
		awningDeviceView.setStatusLighting(awningStatusOnly.getStatusLighting());
		awningDeviceView.setStatusOperationMode(awningLocationOnly.getStatusOperationMode());
		awningDeviceView.setStatusTemperature(awningStatusOnly.getStatusTemperature());
		awningDeviceView.setStatusWindSpeed(awningStatusOnly.getStatusWindSpeed());
		awningDeviceView.setWindSpeedThreshold(awningDefaultOnly.getWindSpeedThreshold());
		awningDeviceView.setLightingCondition(awningStatusOnly.getLightingCondition());
		
		awningDeviceView.setCompany(awningDefaultOnly.getCompany());
		
		if(contractDeta!=null) {
			awningDeviceView.setFinishDate(contractDeta.getContractTerminationDate());
			awningDeviceView.setStartDate(contractDeta.getContractStartDate());
		}
	}
	private void mergeResult(List<AwningUserMapDTO> list, List<AwningLocationOnly> llist, 
			List<AwningStatusOnly> slist, List<AwningDefaultOnly> dlist) {
		for(int i=0;i<llist.size();i++) {
			AwningUserMapDTO mapDTO=new AwningUserMapDTO();
			mapDTO.setAwningId(slist.get(i).getAwningId());
			mapDTO.setLongitude(llist.get(i).getLongitude());
			mapDTO.setLatitude(llist.get(i).getLatitude());
			mapDTO.setInstallationLocationMemo(llist.get(i).getInstallationLocationMemo());
			mapDTO.setManagementNumber(llist.get(i).getManagementNumber());
			mapDTO.setStatusBatteryCharge(slist.get(i).getStatusBatteryCharge());
			mapDTO.setStatusWindSpeed(slist.get(i).getStatusWindSpeed());
			mapDTO.setStatusAwningExpand(slist.get(i).getStatusAwningExpand());
			mapDTO.setStatusTemperature(slist.get(i).getStatusTemperature());
			mapDTO.setStatusLighting(slist.get(i).getStatusLighting());
			mapDTO.setStatusConnected(slist.get(i).getStatusConnected());
			
			mapDTO.setDeviceId(llist.get(i).getDeviceId());
			mapDTO.setControlId(llist.get(i).getControlId());
			mapDTO.setAwningOpenTimeLeft(dlist.get(i).getAwningOpenTimeLeft());
			mapDTO.setAwningOpenTimeRight(dlist.get(i).getAwningOpenTimeRight());
			mapDTO.setLightingCondition(slist.get(i).getLightingCondition());
			mapDTO.setMotorCondition(slist.get(i).getMotorCondition());
			mapDTO.setBatteryCondition(slist.get(i).getBatteryCondition());
			mapDTO.setStatusOperationMode(llist.get(i).getStatusOperationMode());
			
			mapDTO.setCompany(dlist.get(i).getCompany());
			
			list.add(mapDTO);
		}
		
	}
	private void filterResult(List<AwningDeviceView> list,List<String> filterList,List<String> getFilterList) {
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

	private List<String> setFilterList(AwningDeviceView view) {
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
	private void applySearch(List<AwningDeviceView> list,String searchTerm,String searchCriteria) {
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
		int result=awningDefaultRepository.deleteAllByDeviceIdIn(list);
		result+=awningStatusRepository.deleteAllByDeviceIdIn(list);
		result+=awningLocationRepository.deleteAllByDeviceIdIn(list);
		result+=contractDetaRepository.deleteAllByAwningDeviceIdIn(list);
		return result;
	}
	
	

	@Transactional
	private void saveMethodForAddAwning(AwningControl awningControl,Integer manageArea1234,AddModify addModify) {
		
		Integer findAwningId= 
		awningDefaultRepository.findFirstByOrderByAwningIdDesc().getAwningId();
		
		findAwningId++;
		if(awningControl.getAwningId()!=null) {
			if(addModify!=AddModify.ADD)
			findAwningId=awningControl.getAwningId();
		}
		System.out.println("findAwningId:"+findAwningId);
		AwningDefaultOnly awningDefaultOnly= AwningDefaultOnly.builder().awningId(findAwningId).awningOpenTimeLeft(awningControl.getAwningOpenTimeLeft())
		.awningOpenTimeRight(awningControl.getAwningOpenTimeRight()).awningReopenTimeMinutes(awningControl.getAwningReopenTimeMinutes())
		.controlId(awningControl.getControlId()).windSpeedThreshold(awningControl.getWindSpeedThreshold())
		.deviceId(awningControl.getDeviceId()).company(awningControl.getCompany()).build();
		
		AwningStatusOnly awningStatusOnly=AwningStatusOnly.builder().awningId(findAwningId).deviceId(awningControl.getDeviceId())
		.controlId(awningControl.getControlId()).build();
		
		AwningLocationOnly awningLocationOnly=
				AwningLocationOnly.builder().awningId(findAwningId).controlId(awningControl.getControlId())
				.installationLocationMemo(awningControl.getInstallationLocationMemo())
				.latitude(awningControl.getLatitude()).longitude(awningControl.getLongitude())
				.managementArea(manageArea1234).managementNumber(awningControl.getManagementNumber())
				.deviceId(awningControl.getDeviceId()).build();
		
		ContractDeta contractDeta=ContractDeta.builder().awningDeviceId(awningControl.getDeviceId()).awningId(findAwningId)
		.contractStartDate(awningControl.getStartDate()).contractTerminationDate(awningControl.getFinishDate())
		.build();
		ContractDetaLog contractDetaLog=ContractDetaLog.builder().awningDeviceId(awningControl.getDeviceId()).awningId(findAwningId)
				.contractStartDate(awningControl.getStartDate()).contractTerminationDate(awningControl.getFinishDate())
				.build();
		System.out.println("problem1");
		awningDefaultRepository.save(awningDefaultOnly);
		System.out.println("problem2");
		awningLocationRepository.save(awningLocationOnly);
		System.out.println("problem3");
		awningStatusRepository.save(awningStatusOnly);
		System.out.println("problem4");
		contractDetaRepository.save(contractDeta);
		System.out.println("problem5");
		contractDetaLogRepository.save(contractDetaLog);
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
		if(awningControl.getCompany()==null)
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



	@Override
	public HashMap<String, Integer> quickSummaryReply(String token) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> failureSummary=new HashMap<>();
		failureSummary.put("배터리정상", awningStatusRepository.findByBatteryCondition("normal").size());
		failureSummary.put("배터리경고", awningStatusRepository.findByBatteryCondition("warning").size());
		failureSummary.put("배터리고장", awningStatusRepository.findByBatteryCondition("crush").size());
		
		failureSummary.put("모터고장", awningStatusRepository.findByMotorCondition("crush").size());
		failureSummary.put("모터경고", awningStatusRepository.findByMotorCondition("warning").size());
		failureSummary.put("모터정상", awningStatusRepository.findByMotorCondition("normal").size());
		
		failureSummary.put("조명고장", awningStatusRepository.findByLightingCondition("crush").size());
		failureSummary.put("조명경고", awningStatusRepository.findByLightingCondition("warning").size());
		failureSummary.put("조명정상", awningStatusRepository.findByLightingCondition("normal").size());
		
		List<AwningStatusOnly> list=awningStatusRepository.findAll();
		int listcount=0;
		for(AwningStatusOnly awningStatusOnly:list) {
			if(awningStatusOnly.getBatteryCondition().compareTo("normal")==0)
				if(awningStatusOnly.getLightingCondition().compareTo("normal")==0)
					if(awningStatusOnly.getMotorCondition().compareTo("normal")==0)
						listcount+=1;
		}
		
		failureSummary.put("총설치댓수", awningStatusRepository.findAll().size());
		failureSummary.put("정상동작댓수", listcount);
		failureSummary.put("차양막가동댓수", awningStatusRepository.findByStatusAwningExpand("on").size());
		return failureSummary;
	}



	

	

}
