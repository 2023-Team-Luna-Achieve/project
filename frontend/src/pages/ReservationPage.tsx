import React, { useState } from 'react';
import styled from 'styled-components';
import CalendarComponent from '../components/Calendar';
import TimeSelect from '../components/TimeSelect';

const ReservationPageWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center; /* 가로 중앙 정렬을 위해 추가 */
  min-height: 100vh; /* 화면 높이에 따라 컨테이너를 세로 중앙 정렬하려면 필요할 수 있습니다. */
`;

const ContentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center; /* 가로 중앙 정렬을 위해 추가 */
  text-align: center; /* 내용을 중앙 정렬하기 위해 추가 */
`;

const HeaderSection = styled.div`
  display: flex;
  align-items: flex-start; /* 왼쪽 상단 정렬을 위해 추가 */
  text-align: left; /* 텍스트를 왼쪽 정렬하기 위해 추가 */
  margin-top: 10px; /* 상단 여백 추가 */
`;

const Title = styled.h1`
  font-family: 'Inter';
  font-style: normal;
  font-weight: 600;
  font-size: 24px;
  line-height: 29px;
  color: #000000;
  margin: 0; /* 중앙 정렬 제거 */
`;

const LoremText = styled.p`
  font-family: 'Inter';
  font-style: normal;
  font-weight: 400;
  font-size: 16px;
  line-height: 20px;
  color: #c9c9c9;
  margin-top: 10px;
`;

const ProfileImage = styled.div`
  width: 100px;
  height: 100px;
  background: #ffffff;
  border: 1px solid #cccccc;
  border-radius: 50%;
  margin-right: 20px; /* 오른쪽 여백 추가 */
`;

const ProfileImageContainer = styled.div`
  display: flex;
  align-items: center;
  margin-top: 20px;
`;

const TimeSelectContainer = styled.div`
  display: flex;
  justify-content: space-between;
  width: 250px;
  margin-top: 20px;
`;

const ReservationPage: React.FC = () => {
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');

  return (
    <ReservationPageWrapper>
      <ContentWrapper>
        <HeaderSection>
          <ProfileImage />
          <Title>Palo Alto</Title>
        </HeaderSection>
        <HeaderSection>
          <LoremText>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Quo, delectus! Sunt corrupti repellat iste amet
            laboriosam non dolorum iusto laudantium quaerat alias. Quasi odio tenetur porro, nihil nulla facere
            accusamus quibusdam ea voluptatem suscipit!
          </LoremText>
        </HeaderSection>
        <CalendarComponent />
        <TimeSelectContainer>
          <TimeSelect value={startTime} onChange={(selectedTime) => setStartTime(selectedTime)} label="Start Time" />
          <TimeSelect value={endTime} onChange={(selectedTime) => setEndTime(selectedTime)} label="End Time" />
        </TimeSelectContainer>
      </ContentWrapper>
    </ReservationPageWrapper>
  );
};

export default ReservationPage;
