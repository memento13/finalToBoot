package kimsungsu.finalToBoot.service;

import kimsungsu.finalToBoot.entity.Party;
import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.entity.dto.PartyShowDTO;
import kimsungsu.finalToBoot.repository.PartyMemberRepository;
import kimsungsu.finalToBoot.repository.PartyRepository;
import kimsungsu.finalToBoot.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.persistence.EntityManager;

import java.util.List;
import java.util.UUID;


@SpringBootTest
class PartyServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PartyRepository partyRepository;
    @Autowired
    PartyMemberRepository partyMemberRepository;
    @Autowired
    PartyService partyService;



    User user = null;
    @BeforeEach
    void setTest(){
        user = userRepository.findOneByEmailAndPassword("test@test.com","1q2w3e4r5t6y!");
        if(user==null){
            user = new User();

            String uuid = UUID.randomUUID().toString();
            user.setId(uuid);
            user.setEmail("test@test.com");
            user.setPassword("1q2w3e4r5t6y!");
            user.setName("KIM");
            userRepository.save(user);
            User findUser = userRepository.findOneById(uuid);

            Assertions.assertThat(user.getName()).isEqualTo(findUser.getName());
        }

    }

    @Test
    public void 파티생성() throws Exception{

        boolean result = partyService.createParty(user, "testParty2");
        Party testParty = partyRepository.findOneByName("testParty2");
        if(result){
            Assertions.assertThat(testParty).isNotNull();
        }
    }

    @Test
    public void 중복이름파티생성방지() throws Exception{
        partyService.createParty(user, "testParty");
        boolean result = partyService.createParty(user, "testParty");
        Assertions.assertThat(result).isFalse();

    }

    @Test
    public void 파티리더확인() throws Exception{
        Party party = partyRepository.findOneByName("test3");
        Assertions.assertThat(party.getLeader().getId()).isEqualTo(user.getId());
    }

    @Test
    public void 파티리더인_파티목록() throws Exception{

        List<PartyShowDTO> dtoList = partyService.partiesWhereIamLeader(user);
        Assertions.assertThat(dtoList.size()).isEqualTo(4);

    }

    @Test
    public void 파티멤버인_파티목록() throws Exception{

        List<PartyShowDTO> dtoList = partyService.partiesWhereIamMemberV2(user);
        Assertions.assertThat(dtoList.size()).isEqualTo(0);

    }

    @Test
    public void 파티리더인_파티목록V2() throws Exception{

        List<PartyShowDTO> dtoList = partyService.partiesWhereIamLeaderV2(user);
        Assertions.assertThat(dtoList.size()).isEqualTo(4);

    }

    @Test
    public void 파티가입확인실패() throws Exception{
        Boolean result = partyService.checkUserJoinParty(user, "test3124245");
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void 파티가입확인성공() throws Exception{
        Boolean result = partyService.checkUserJoinParty(user, "test3");
        Assertions.assertThat(result).isTrue();
    }


}