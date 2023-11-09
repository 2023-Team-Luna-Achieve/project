import React from 'react';
import styled from 'styled-components';
const PageContainer = styled.div`
  background-color: #ececec;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  max-width: 100%;
  margin: 0 auto;
  padding: 20px;
`;
const Box = styled.div`
  width: calc(20%);
  height: 180px;
  background-color: #ffffff;
  border: 1px solid #e4e4e4;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 10px;
`;
const Title = styled.div`
  font-weight: bold;
  color: #000000;
  font-size: 18px;
  margin-bottom: 10px;
  text-align: left;
  margin-top: 10px;
  margin-left: 10px;
`;
const Content = styled.div`
  color: #575757;
  font-size: 14px;
  text-align: left;
  margin-left: 10px;
  margin-right: 10px;
  margin-bottom: 10px;
`;
const CommunityPage: React.FC = () => {
  return (
    <PageContainer>
      <Box>
        <Title>Achieve 프로젝트 팀 공지</Title>
        <Content>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Quo, delectus! Sunt corrupti repellat iste amet
          laboriosam non dolorum iusto
        </Content>
      </Box>
      <Box>
        <Title>공지 2</Title>
        <Content>내용 2</Content>
      </Box>
      <Box>
        <Title>공지 3</Title>
        <Content>내용 3</Content>
      </Box>
      <Box>
        <Title>공지 4</Title>
        <Content>내용 4</Content>
      </Box>
      <Box>
        <Title>공지 5</Title>
        <Content>내용 5</Content>
      </Box>
      <Box>
        <Title>공지 6</Title>
        <Content>내용 6</Content>
      </Box>
      <Box>
        <Title>공지 7</Title>
        <Content>내용 7</Content>
      </Box>
      <Box>
        <Title>공지 8</Title>
        <Content>내용 8</Content>
      </Box>
      <Box>
        <Title>공지 9</Title>
        <Content>내용 9</Content>
      </Box>
      <Box>
        <Title>공지 10</Title>
        <Content>내용 10</Content>
      </Box>
      <Box>
        <Title>공지 11</Title>
        <Content>내용 11</Content>
      </Box>
      <Box>
        <Title>공지 12</Title>
        <Content>내용 12</Content>
      </Box>
    </PageContainer>
  );
};
export default CommunityPage;
