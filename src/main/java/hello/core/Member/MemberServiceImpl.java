package hello.core.Member;

// 관습) 구현체가 1개 있을 때는, 인터페이스명 뒤에 Impl만 붙임
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
