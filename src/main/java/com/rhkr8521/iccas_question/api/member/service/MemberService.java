package com.rhkr8521.iccas_question.api.member.service;

import com.rhkr8521.iccas_question.api.member.domain.Member;
import com.rhkr8521.iccas_question.api.member.repository.MemberRepository;
import com.rhkr8521.iccas_question.api.member.dto.MemberResponseDTO;
import com.rhkr8521.iccas_question.api.result.domain.GameSet;
import com.rhkr8521.iccas_question.api.result.repository.GameSetRepository;
import com.rhkr8521.iccas_question.common.exception.NotFoundException;
import com.rhkr8521.iccas_question.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final GameSetRepository gameSetRepository;

    @Transactional
    public MemberResponseDTO checkFirstTimeUser(String userId) {
        Optional<Member> memberOptional = memberRepository.findByUserId(userId);

        if (memberOptional.isPresent()) {
            return MemberResponseDTO.builder()
                    .result(false)
                    .build();
        } else {
            Member newMember = Member.builder()
                    .userId(userId)
                    .build();
            memberRepository.save(newMember);
            return MemberResponseDTO.builder()
                    .result(true)
                    .build();
        }
    }

    @Transactional
    public void checkCorrectResult(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER.getMessage()));

        List<GameSet> currentGameSetCarousel = gameSetRepository.findByMemberAndTheme(member, "carousel");
        List<GameSet> currentGameSetFerrisWheel = gameSetRepository.findByMemberAndTheme(member, "ferris_wheel");
        List<GameSet> currentGameSetRollerCoaster = gameSetRepository.findByMemberAndTheme(member, "roller_coaster");

        resetStageRecordsIfNecessary(currentGameSetCarousel);
        resetStageRecordsIfNecessary(currentGameSetFerrisWheel);
        resetStageRecordsIfNecessary(currentGameSetRollerCoaster);
    }

    private void resetStageRecordsIfNecessary(List<GameSet> gameSets) {
        gameSets.forEach(gs -> {
            int totalStagesCount = gs.getFirstStageTotalCount() + gs.getSecondStageTotalCount() + gs.getThirdStageTotalCount();
            if (totalStagesCount < 11) {
                GameSet updatedGameSet = gs.resetStageRecords();
                gameSetRepository.save(updatedGameSet);
            }
        });
    }
}
