package edu.mum.waa.repository;

import edu.mum.waa.entity.Block;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepo extends CrudRepository<Block,Long> {
}
