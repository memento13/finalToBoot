package kimsungsu.finalToBoot.service;

import kimsungsu.finalToBoot.entity.Party;
import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;

    public boolean createParty(User user, Party party){
        System.out.println("PartyService.createParty");
        boolean result = false;
        /**
         * 파티 생성시
         *
         */


        return result;
    }

}
