import React from 'react';
import styled from 'styled-components';
import NoticePagePage from './CommunityPage';

const PageWrapper = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  background: #ffffff;
`;

const NoticeText = styled.div`
  position: absolute;
  width: 210px;
  height: 70px;
  left: 680px;
  top: 100px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 200;
  font-size: 60px;
  line-height: 80px;
  color: #000000;
`;

const BlackLine = styled.div`
  position: absolute;
  width: 1320px;
  height: 2px;
  left: 60px;
  top: 200px;
  background: #000000;
`;

const Title = styled.h1`
  position: absolute;
  width: 513px;
  height: 42px;
  left: 670px;
  top: 220px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 600;
  font-size: 34px;
  line-height: 41px;
  color: #000000;
`;

const MetaInfoWrapper = styled.div`
  position: absolute;
  width: 697px;
  height: 42px;
  left: 940px;
  top: 270px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 300;
  font-size: 24px;
  line-height: 41px;
  color: #e4e4e4;
`;

const Content = styled.p`
  position: absolute;
  width: 1797px;
  height: 368px;
  left: 63px;
  top: 300px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 400;
  font-size: 24px;
  line-height: 40px;
  color: #575757;
`;

const BackButton = styled.button`
  position: absolute;
  width: 110px;
  height: 50px;
  left: 1250px;
  top: 650px;
  background: #1c1c1c;
  border-radius: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
`;

const BackButtonText = styled.span`
  font-family: 'Inter';
  font-style: normal;
  font-weight: 300;
  font-size: 28px;
  line-height: 34px;
  color: #ffffff;
`;

const CommunityPage: React.FC = () => {
  return (
    <PageWrapper>
      <NoticeText>공지</NoticeText>
      <BlackLine />
      <Title>공지 제목</Title>
      <MetaInfoWrapper>작성자 안나경 | 조회수 100 | 게시일 2023.11.7</MetaInfoWrapper>
      <Content>
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Quo, delectus! Sunt corrupti repellat iste amet
        laboriosam non dolorum iusto laudantium quaerat alias. Quasi odio tenetur porro, nihil nulla facere accusamus
        quibusdam ea voluptatem suscipit! Perferendis, maiores. Esse ratione molestiae a deleniti tenetur. Facilis atque
        quae culpa nulla fuga totam aliquid cumque nisi maxime repellendus. Dolorum, natus iusto facilis velit incidunt
        nisi. Facilis atque quae culpa nulla fuga totam aliquid cumque nisi maxime repellendus. Dolorum, natus iusto
        facilis velit incidunt nisi. Facilis atque quae culpa nulla fuga totam aliquid cumque nisi maxime repellendus.
        Dolorum, natus iusto facilis velit incidunt nisi.
      </Content>
      <BackButton>
        <BackButtonText>목록</BackButtonText>
      </BackButton>
    </PageWrapper>
  );
};

export default CommunityPage;
