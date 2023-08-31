package com.sparta.lv1memo.service;

import com.sparta.lv1memo.dto.MemoRequestDto;
import com.sparta.lv1memo.dto.MemoResponseDto;
import com.sparta.lv1memo.entity.Memo;
import com.sparta.lv1memo.repository.MemoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemoService {
    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        //RequestDto -> Entity
        Memo memo = new Memo(requestDto);
        //DB 저장
        Memo saveMemo = memoRepository.save(memo);
        //Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(saveMemo);

        return memoResponseDto;
    }


    public List<MemoResponseDto> getMemos() {
        //DB 조회
        return memoRepository.findAllByOrderByModifiedAtDesc().stream().map(MemoResponseDto::new).toList();
    }


    public Memo getMemo(Long id) {
        //해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);
        return memo;
    }

    @Transactional
    public ResponseEntity<String> updateMemo(Long id, MemoRequestDto requestDto) {
        //해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);
            //memo 수정
        memo.update(requestDto);
        //수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인
        if (requestDto.getPassword().equals(memo.getPassword())) {
//                return id;
            return ResponseEntity.ok("수정 성공!");
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


    public ResponseEntity<String> deleteMemo(Long id, MemoRequestDto requestDto) {
        //해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);
            // 삭제 시 비밀번호 확인
            if (requestDto.getPassword().equals(memo.getPassword())) {
                // memo 삭제
                memoRepository.delete(memo);
                return ResponseEntity.ok("삭제 성공!");
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
    }

    private Memo findMemo(Long id) {
        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
    }


}
