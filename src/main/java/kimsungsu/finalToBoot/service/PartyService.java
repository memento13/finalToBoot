package kimsungsu.finalToBoot.service;

import kimsungsu.finalToBoot.entity.Party;
import kimsungsu.finalToBoot.entity.PartyMember;
import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.entity.dto.PartyShowDTO;
import kimsungsu.finalToBoot.repository.PartyMemberRepository;
import kimsungsu.finalToBoot.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;
    private final PartyMemberRepository partyMemberRepository;

    /**
     * 파티 생성 로직
     * 파티 생성시 이름 중복확인
     * 중복시 false 반환
     * 파티 생성가능하면 파티 생성, 파티멤버에 리더 생성
     * true 반환
     *
     * @param user      파티리더가 될 파티 생성자
     * @param partyName 생성할 파티 이름
     * @return 성공시 true, 실패시 false 반환
     */
    public boolean createParty(User user, String partyName) {
        System.out.println("PartyService.createParty");
        boolean result = false;

        Party isExist = partyRepository.findOneByName(partyName);
        if (isExist == null) {
            //파티생성
            Party party = new Party();
            party.setId(UUID.randomUUID().toString());
            party.setName(partyName);
            party.setLeader(user);
            partyRepository.save(party);

            //파티리더생성
            PartyMember leader = new PartyMember(party, user, 100);
            partyMemberRepository.save(leader);
            result = true;
        }

        return result;
    }

    /**
     * 해당하는 User가 리더인 파티를 찾음
     * @param user
     * @return 파티리스트를 arrayList로 반환
     */
    public List<PartyShowDTO>partiesWhereIamLeader(User user){
        List<PartyShowDTO> result = new ArrayList<>();

        //반드시 개선해야함.... 아마 엔티티 구조자체를 뜯어고쳐야 할듯
        //파티 id 값만 되고 파티 객체 자체는 연관되지 않음
        List<PartyMember> partyMembers = partyMemberRepository.findByUserAndGrade(user, 100);

        for (PartyMember partyMember : partyMembers) {
            Party party = partyRepository.findOneById(partyMember.getId().getPartyId());
            result.add(new PartyShowDTO(party,user.getId()));
        }

        return result;
    }

    /**
     * 해당하는 User가 멤버인 파티를 찾음
     * @param user
     * @return 파티리스트를 arrayList로 반환
     */
    public List<PartyShowDTO>partiesWhereIamMember(User user){
        List<PartyShowDTO> result = new ArrayList<>();

        //반드시 개선해야함.... 아마 엔티티 구조자체를 뜯어고쳐야 할듯
        List<PartyMember> partyMembers = partyMemberRepository.findByUserAndGrade(user, 1);

        for (PartyMember partyMember : partyMembers) {
            Party party = partyRepository.findOneById(partyMember.getId().getPartyId());
            result.add(new PartyShowDTO(party,user.getId()));
        }

        return result;
    }

}
