import React, { useState } from 'react';
import styled from 'styled-components';
import CalendarComponent from '../components/Calendar';
import TimeSelect from '../components/TimeSelect';

const ReservationPageWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 50vh;
  overflow: hidden;
`;

const ContentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
`;

const HeaderSection = styled.div`
  display: flex;
  align-items: flex-start; /* 왼쪽 정렬로 변경 */
  text-align: left; /* 왼쪽 정렬로 변경 */
  margin-top: 10px;
`;

const Title = styled.h1`
  font-family: 'Inter';
  font-style: normal;
  font-weight: 600;
  font-size: 24px;
  line-height: 29px;
  color: #000000;
  margin-top: 40px;
`;

const LoremText = styled.p`
  font-family: 'Inter';
  font-style: normal;
  font-weight: 400;
  font-size: 16px;
  line-height: 20px;
  color: #c9c9c9;
  margin-top: 10px;
  margin-left: 30px;
  text-align: left;
`;

const ProfileImage = styled.div`
  width: 100px;
  height: 100px;
  background: #ffffff;
  border: 1px solid #cccccc;
  border-radius: 50%;
  margin-right: 20px;
`;

const ProfileImageAndTitle = styled.div`
  display: flex;
  align-items: flex-start;
  margin-right: 1200px;
  margin-top: 10px;
`;

const TimeSelectContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 250px;
  margin-top: 20px;
`;

const Button = styled.button`
  background-color: #a5a5a5;
  border: 2px solid #a5a5a5;
  padding: 10px 40px;
  margin-left: 10px;
  color: #ffffff;
  font-weight: 600;
  font-size: 16px;
  margin-top: 20px;
  cursor: pointer;
  border-radius: 5px;
  transition:
    background-color 0.3s,
    border-color 0.3s,
    color 0.3s;

  &:hover {
    background-color: #000000;
    border-color: #000000;
  }
`;
const Separator = styled.span`
  margin: 0 10px;
`;

const ReservationPage: React.FC = () => {
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');

  const handleReservation = () => {
    if (startTime && endTime) {
      fetch('https://your-backend-api.com/reservation', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ startTime, endTime }),
      })
        .then((response) => {
          if (response.ok) {
            console.log('Reservation successful');
          } else {
            console.error('Reservation failed');
          }
        })
        .catch((error) => {
          console.error('Reservation failed:', error);
        });
    }
  };

  return (
    <ReservationPageWrapper>
      <ContentWrapper>
        <HeaderSection>
          <ProfileImageAndTitle>
            <ProfileImage />
            <Title>Palo Alto</Title>
          </ProfileImageAndTitle>
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
          <TimeSelect value={startTime} onChange={(selectedTime) => setStartTime(selectedTime)} label="시작 시간" />
          <Separator>~</Separator>
          <TimeSelect value={endTime} onChange={(selectedTime) => setEndTime(selectedTime)} label="종료 시간" />
        </TimeSelectContainer>
        <Button onClick={handleReservation}>예약하기</Button>
      </ContentWrapper>
    </ReservationPageWrapper>
  );
};

export default ReservationPage;
