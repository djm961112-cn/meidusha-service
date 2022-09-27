package com.fkmalls.meidusha.meidusha.service.demand;

import com.fkmalls.meidusha.meidusha.dto.Demand.DemandDto;
import com.fkmalls.meidusha.meidusha.entity.demand.DemandEntity;
import com.fkmalls.meidusha.meidusha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * dengjinming
 * 2022/8/19
 */
@Service
public class DemandService implements DemandDto {
    @Autowired
    DemandDto demandDto;
    @Autowired
    UserService userService;

    @Override
    public int addDemand(Long id,String demandName,String demandAddress,Long productId,Long qaId,Long devId,Long frontId,String createUser){
        return demandDto.addDemand(id,demandName,demandAddress,productId,qaId,devId,frontId,createUser);
    }

    @Override
    public List<DemandEntity> selectAllDemand(DemandEntity demandEntityNew){
        List<DemandEntity> demandEntityList=demandDto.selectAllDemand(demandEntityNew);
        for (DemandEntity demandEntity:demandEntityList){
            if (demandEntity.getProductId()!=null){
                demandEntity.setProductName(userService.findUserByUserId(demandEntity.getProductId()).get(0).getNickName());
            }
            if (demandEntity.getQaId()!=null){
                demandEntity.setQaName(userService.findUserByUserId(demandEntity.getQaId()).get(0).getNickName());
            }
            if (demandEntity.getDevId()!=null){
                demandEntity.setDevName(userService.findUserByUserId(demandEntity.getDevId()).get(0).getNickName());
            }
            if (demandEntity.getFrontId()!=null){
                demandEntity.setFrontName(userService.findUserByUserId(demandEntity.getFrontId()).get(0).getNickName());
            }
        }
        return demandEntityList;
    }

    @Override
    public int updateDemandStatusById(int demandStatus,String updateUser, Long id){
        return demandDto.updateDemandStatusById(demandStatus,updateUser,id);
    }

    @Override
    public int updateReportStatusById(int reportStatus,Long id){
        return demandDto.updateReportStatusById(reportStatus,id);
    }

}
