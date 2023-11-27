package com.donothing.swithme.repository;

import com.donothing.swithme.domain.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
//    Page<Study> findStudy(Pageable pageable);


}
