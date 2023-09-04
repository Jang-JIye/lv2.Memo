package com.sparta.lv1memo.service;


import com.sparta.lv1memo.dto.MemoRequestDto;
import com.sparta.lv1memo.dto.MemoResponseDto;
import com.sparta.lv1memo.entity.Memo;
import com.sparta.lv1memo.entity.User;
import com.sparta.lv1memo.entity.UserRoleEnum;
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

    public MemoResponseDto createMemo(MemoRequestDto requestDto, User user) {
        //RequestDto -> Entity
        Memo memo = memoRepository.save(new Memo(requestDto, user));

        //Entity -> ResponseDto
        return new MemoResponseDto(memo);
    }


    public List<MemoResponseDto> getMemos(User user) {
        //DB 조회
        UserRoleEnum userRoleEnum = user.getRole();
        List<Memo> memoList;

        if(userRoleEnum == UserRoleEnum.USER) {
            memoList = memoRepository.findAllByUserOrderByModifiedAtDesc(user);  // 유저권한, 현재 유저가 작성한 게시글만 조회
        } else {
            memoList = memoRepository.findAllByOrderByModifiedAtDesc();  // 관리자권한, 모든 메모 조회
        }
        return memoList.stream().map(MemoResponseDto::new).toList();
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
