package com.heartscopy.heartsocpyBeckEnd.service.lenzeservice;

import com.heartscopy.heartsocpyBeckEnd.domain.lenze.Lenze;
import com.heartscopy.heartsocpyBeckEnd.dto.lenze.LenzeSearchResultDto;
import com.heartscopy.heartsocpyBeckEnd.repository.lenzerepository.LenzeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LenzeService {
    private final LenzeRepository lenzeRepository;

    /**
     * 렌즈 생성 시 작성자 ID(uid)를 반드시 외부에서 받아서 설정하도록 변경
     */
    public Lenze createLenze(Lenze lenze, String uid) {
        // 예: uid가 숫자가 아니라면 별도 매핑 필요
        // Long writerId = Long.parseLong(uid);
        // lenze.setWriterId(writerId);
        lenze.setWriterId(uid);  // uid 타입에 따라 적절히 변환하세요
        return lenzeRepository.save(lenze);
    }

    /**
     * ID로 렌즈 조회 (권한 체크는 호출 측에서)
     */
    public Lenze findById(Long id) {
        return lenzeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 렌즈가 존재하지 않습니다: " + id));
    }

    /**
     * 삭제 시 uid를 받아 권한 체크 수행
     */
    @Transactional
    public void deleteById(Long id, String uid) {
        Lenze lenze = findById(id);
        if (!lenze.getWriterId().equals(Long.parseLong(uid))) {
            throw new SecurityException("권한이 없습니다.");
        }
        lenzeRepository.deleteById(id);
    }

    /**
     * 페이징 조회
     */
    public Page<Lenze> getLenzesByPage(int page, int size) {
        return lenzeRepository.findAll(PageRequest.of(page, size));
    }

    /**
     * 검색
     */
    public List<LenzeSearchResultDto> searchLenzes(String keyword, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Lenze> resultPage = lenzeRepository.findByTopicContainingOrderByCreatedAtDesc(keyword, pageable);
        return resultPage.map(l -> new LenzeSearchResultDto(l.getLenzeId(), l.getTopic()))
                .getContent();
    }

    /**
     * 최근 렌즈 리스트
     */
    public List<Lenze> getRecentLenzes() {
        return lenzeRepository.findTop10ByOrderByCreatedAtDesc();
    }
}