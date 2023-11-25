import React from 'react';
import styled from 'styled-components';

const IntroContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: flex-start;
  height: 80vh;
  padding: 20px;
`;

interface IntroTextProps {
  size?: string;
  color?: string;
}

const IntroText = styled.div<IntroTextProps>`
  font-size: ${(props) => props.size || 'inherit'};
  text-align: left;
  margin: 10px;
  color: ${(props) => props.color || 'inherit'};
  font-family: 'Arial', sans-serif;
`;

const AboutPage: React.FC = () => {
  return (
    <IntroContainer>
      <IntroText size="36px" color="#C0C0C0">
        About this service...
      </IntroText>{' '}
      <IntroText size="20px" color="#C0C0C0">
        이 서비스에 대하여
      </IntroText>{' '}
      <IntroText size="20px">
        이 서비스는 테커 팀루나 프로젝트팀 Achieve에서 만든 테커 동아리방 예약 시스템 입니다.
      </IntroText>
    </IntroContainer>
  );
};

export default AboutPage;
