package kimsungsu.finalToBoot.repository;

import kimsungsu.finalToBoot.entity.Party;
import kimsungsu.finalToBoot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party,String> {

    Party findOneByName(String partyName);
    Party findOneById(String id);
    List<Party> findByLeader(User leader);



}
