package com.example.rmxline.Repository;

import com.example.rmxline.Model.RCCModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RCCRepository extends JpaRepository<RCCModel,Long> {

}
