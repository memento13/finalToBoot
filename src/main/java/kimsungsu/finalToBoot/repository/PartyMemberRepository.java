package kimsungsu.finalToBoot.repository;

import kimsungsu.finalToBoot.entity.Party;
import kimsungsu.finalToBoot.entity.PartyMember;
import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.entity.compositeKey.PartyMemberPK;
import kimsungsu.finalToBoot.entity.dto.PartyShowDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyMemberRepository extends JpaRepository<PartyMember,PartyMemberPK> {

    List<PartyMember> findByUserAndGrade(User user,Integer grade);
    PartyMember findOneByUserAndParty(User user, Party party);
}
