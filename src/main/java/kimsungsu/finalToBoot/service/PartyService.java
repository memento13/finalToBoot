package kimsungsu.finalToBoot.service;

import kimsungsu.finalToBoot.entity.Party;
import kimsungsu.finalToBoot.entity.PartyMember;
import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.entity.dto.PartyShowDTO;
import kimsungsu.finalToBoot.repository.PartyMemberRepository;
import kimsungsu.finalToBoot.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        //파티 id 값만 되고 파티 객체 자체는 연관되지 않음
        //이유 : jpa가 프록시 객체로 불러와서 지연로딩으로 인해서 party에 id를 제외하고 null 값만 담김
        //해결방법 : 해당 메서드에서 일어나는 쿼리들을 트랜잭션으로 취급해서 시도하면 됨
        //           @Transactional 을 붙이자. V2 제작으로 해결함.
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

        List<PartyMember> partyMembers = partyMemberRepository.findByUserAndGrade(user, 1);

        for (PartyMember partyMember : partyMembers) {
            Party party = partyRepository.findOneById(partyMember.getId().getPartyId());
            result.add(new PartyShowDTO(party,user.getId()));
        }

        return result;
    }

    @Transactional
    public List<PartyShowDTO>partiesWhereIamMemberV2(User user){
        List<PartyShowDTO> result = new ArrayList<>();

        List<PartyMember> partyMembers = partyMemberRepository.findByUserAndGrade(user, 1);

        for (PartyMember partyMember : partyMembers) {
            System.out.println("partyMember = " + partyMember.getParty().getId());
            result.add(new PartyShowDTO(partyMember.getParty(),user.getId()));
        }

        return result;
    }

    @Transactional
    public List<PartyShowDTO>partiesWhereIamLeaderV2(User user){
        List<PartyShowDTO> result = new ArrayList<>();

        List<PartyMember> partyMembers = partyMemberRepository.findByUserAndGrade(user, 100);

        for (PartyMember partyMember : partyMembers) {
            System.out.println("partyMember = " + partyMember.getParty().getId());
            System.out.println("partyMember.getParty().getName() = " + partyMember.getParty().getName());
            result.add(new PartyShowDTO(partyMember.getParty(),user.getId()));
        }

        return result;
    }

    /**
     * 해당 유저가 파티에 가입되어 있는지 확인하는 함수
     * @param user 확인할 유저
     * @param partyName 확인할 파티이름
     * @return Boolean 값으로 파티에 가입되거나 리더면 true 반환 아니면 false 반환
     */
    @Transactional
    public Boolean checkUserJoinParty(User user,String partyName){
        Boolean result = false;
        PartyMember partyMember = null;
        Party party = partyRepository.findOneByName(partyName);
        partyMember= partyMemberRepository.findOneByUserAndParty(user, party);

        if(partyMember != null){
            result = true;
        }
        return result;
    }

    /**
     * 해당 유저가 파티에 가입되어 있으면 파티 정보를 넘겨주는 함수
     * @param user 확인할 유저
     * @param partyName 확인할 파티이름
     * @return 파티에 가입되거나 리더면 PartyShowDTO로 반환 아니면 null 값 반환
     */
    @Transactional
    public PartyShowDTO showPartyInfoIfUserJoin(User user, String partyName){
        PartyShowDTO result = null;
        PartyMember partyMember = null;
        Party party = partyRepository.findOneByName(partyName);
        partyMember= partyMemberRepository.findOneByUserAndParty(user, party);

        if(partyMember != null){
            result = new PartyShowDTO(partyMember.getParty(),user.getId());
        }

        return result;
    }
}
