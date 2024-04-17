package com.example.rmxline.Repository;

import com.example.rmxline.Model.UserLineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLineRepository extends JpaRepository<UserLineModel,String> {
    UserLineModel findByUserToken(String replyToken);

    @Query(value = "SELECT * FROM user_line WHERE read_massage = 0 AND status = 0 ORDER BY rcc_time DESC;",nativeQuery = true)
    List<UserLineModel> findUserNew();
    @Query(value = "SELECT * FROM user_line WHERE read_massage = 1 AND status = 0 ORDER BY rcc_time DESC;",nativeQuery = true)
    List<UserLineModel> findUserProcess();
    @Query(value = "SELECT * FROM user_line WHERE read_massage = 1 AND status = 1 ORDER BY rcc_time DESC;",nativeQuery = true)
    List<UserLineModel> findUserFinish();
}
