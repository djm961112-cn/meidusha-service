package com.fkmalls.meidusha.meidusha.dto;

import com.fkmalls.meidusha.meidusha.entity.CaseEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CaseDto {
    @Select(value = "SELECT * FROM meidusha_case.`case` where case_del_tag=0;")
    List<CaseEntity> getCaseList();

    //INSERT INTO `meidusha_case`.`case` (`p_id`,`case_code`,`case_title`,`case_tag`,`case_del_tag`) VALUES (NULL,'CASE2207080002','测试标题4',0,0);
    @Insert(value = "INSERT INTO `meidusha_case`.`case`(`id`,`p_id`,`case_code`,`case_title`,`case_tag`,`case_del_tag`) " +
            "VALUES (#{id},#{pId},#{caseCode},#{caseTitle},#{caseTag},#{caseDelTag});")
    int saveCaseWithoutCaseCondition(
            @Param("id") Long id,
            @Param("pId") Long pId,
            @Param("caseCode") String caseCode,
            @Param("caseTitle") String caseTitle,
            @Param("caseTag") int caseTag,
            @Param("caseDelTag") int caseDelTag
    );

    @Select(value = "SELECT mc.case_code FROM `meidusha_case`.case mc ORDER BY mc.create_time DESC LIMIT 1;")
    String findLastCode();

    @Select(value = "SELECT mc.id FROM `meidusha_case`.case mc ORDER BY mc.create_time DESC LIMIT 1;")
    Integer findLastId();

    //UPDATE `meidusha_case`.`case` SET `p_id`=996869407156207616,`case_code`='CASE2207130002',`case_title`='叶子节点6,pid=2,id=6',`case_condition`='a',`case_expected_results`=NULL,`case_actually_results`=NULL,`case_operation_steps`=NULL,`update_user`=NULL WHERE `id`=996869407336562688;
    @Update(value = "UPDATE `meidusha_case`.`case` SET `case_code`=#{caseCode},`case_title`=#{caseTitle},`case_condition`=#{caseCondition},`case_expected_results`=#{caseExpectedResults},`case_actually_results`=#{caseActuallyResults},`case_operation_steps`=#{caseOperationSteps},`update_user`=#{updateUser} WHERE `id`=#{id};")
    int fixCase(
            @Param("caseCode") String caseCode,
            @Param("caseTitle") String caseTitle,
            @Param("caseCondition") String caseCondition,
            @Param("caseExpectedResults") String caseExpectedResults,
            @Param("caseActuallyResults") String caseActuallyResults,
            @Param("caseOperationSteps") String caseOperationSteps,
            @Param("updateUser") String updateUser,
            @Param("id") Long id
            );

    //UPDATE `meidusha_case`.`case` SET `case_del_tag`=1 WHERE id=996869407336562688;
    @Update(value = "UPDATE `meidusha_case`.`case` SET `case_del_tag`=1 WHERE id=#{id};")
    void deleteCase(
            @Param("id") Long id
    );

    //UPDATE `meidusha_case`.`case` SET `p_id`=1001862582656565248 WHERE `id`=1001862582681731072;
    @Update(value = "UPDATE `meidusha_case`.`case` SET `p_id`=#{newPid} WHERE `id`=#{id};")
    int updateCasePid(
            @Param("newPid") Long newPid,
            @Param("id") Long id
    );

    //INSERT INTO `meidusha_case`.`case` (`id`,`p_id`,`case_code`,`case_title`) VALUES (1,2,'1111','222222');
    @Insert(value = "INSERT INTO `meidusha_case`.`case` (`id`,`p_id`,`case_code`,`case_title`) VALUES (#{id},#{pId},#{caseCode},#{caseTitle});")
    int addCase(
            @Param("id") Long id,
            @Param("pId") Long pId,
            @Param("caseCode") String caseCode,
            @Param("caseTitle") String caseTitle
    );

    //SELECT * FROM `case` WHERE id='1001862582656565248';
    @Select(value = "SELECT * FROM `meidusha_case`.`case` WHERE id=#{id};")
    CaseEntity selectCaseById(
            @Param("id") Long id
    );

    //SELECT p_id FROM `case` WHERE id='1001862582656565248';
    @Select(value = "SELECT p_id FROM `case` WHERE id=#{id} and case_del_tag=0;")
    Long selectCasePidByCaseId(
            @Param("id") Long id
    );

    @Select(value = "SELECT id FROM `case` WHERE p_id=#{pId} and case_del_tag=0;")
    List<Long> selectCaseIdByPid(
            @Param("pId") Long pId
    );
}
