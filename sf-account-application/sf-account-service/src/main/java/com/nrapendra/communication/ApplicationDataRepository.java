package com.nrapendra.communication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationDataRepository extends JpaRepository<ApplicationData,Long> {

    @Query(nativeQuery=true, value = "SELECT ID,HTTP_STATUS_CODE,REQUEST,RESPONSE FROM APPLICATION_DATA" )
    List<ApplicationData> findByIdAndHttpStatusCodeRequestAndResponse();
}
