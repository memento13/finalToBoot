package kimsungsu.finalToBoot.repository;

import kimsungsu.finalToBoot.entity.PartyMember;
import kimsungsu.finalToBoot.entity.compositeKey.PartyMemberPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyMemberRepository extends JpaRepository<PartyMember,PartyMemberPK> {
}
