package com.botmg3002.canteen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.botmg3002.canteen.model.SubItemType;
import java.util.List;


@Repository
public interface SubItemTypeRepository extends JpaRepository<SubItemType, Long>{
    List<SubItemType> findByItemId(Long id);

    boolean existsById(@NonNull Long id);
}
