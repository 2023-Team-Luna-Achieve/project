import React, { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import styled from 'styled-components';
import TimeSelect from '../components/TimeSelect';
import axios from '../util/axiosConfig';

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
  align-items: flex-start;
  text-align: left;
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
  const [selectedDate, setSelectedDate] = useState<Date | null>(null); // 초기 상태를 Date | null로 수정
  const [startTime, setStartTime] = useState<string>('');
  const [endTime, setEndTime] = useState<string>('');
  const [members, setMembers] = useState<number>(0);

  const handleDateChange = (date: Date | Date[] | null) => {
    setSelectedDate(date as Date | null);
  };

  const handleReservation = async () => {
    console.log('selectedDate:', selectedDate);
    console.log('startTime:', startTime);
    console.log('endTime:', endTime);
    console.log('members:', members);

    if (selectedDate && startTime && endTime && members > 0) {
      const reservationStartTime = new Date(selectedDate);
      reservationStartTime.setHours(Number(startTime.split(':')[0]), Number(startTime.split(':')[1]));

      const reservationEndTime = new Date(selectedDate);
      reservationEndTime.setHours(Number(endTime.split(':')[0]), Number(endTime.split(':')[1]));

      // 한국 시간으로 변환
      const koreanTimeZoneOffset = 9 * 60; // 한국 시간은 UTC+9
      reservationStartTime.setMinutes(reservationStartTime.getMinutes() + koreanTimeZoneOffset);
      reservationEndTime.setMinutes(reservationEndTime.getMinutes() + koreanTimeZoneOffset);

      // ISO 문자열 생성 후 .000Z 제거
      const isoStartTime = reservationStartTime.toISOString().replace(/\.000Z$/, '');
      const isoEndTime = reservationEndTime.toISOString().replace(/\.000Z$/, '');

      console.log('reservationStartTime:', isoStartTime);
      console.log('reservationEndTime:', isoEndTime);

      try {
        // axios를 사용하여 API 호출
        const response = await axios.post('http://localhost:8080/api/reservation', {
          reservationStartTime: reservationStartTime.toISOString(),
          reservationEndTime: reservationEndTime.toISOString(),
          members,
          meetingRoomId: 1,
        });

        if (response.status === 201) {
          console.log('Reservation successful');
        } else {
          console.error('Reservation failed');
        }
      } catch (error) {
        console.error('Reservation failed:', error);
      }
    } else {
      console.error('Please fill in all fields');
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
        <div>
          <Calendar
            onChange={handleDateChange}
            value={selectedDate}
            formatDay={(_, date) => (date instanceof Date ? date.getDate().toString() : '')}
          />
        </div>
        <TimeSelectContainer>
          <TimeSelect
            value={startTime}
            onChange={(selectedTime: string) => setStartTime(selectedTime)}
            label="시작 시간"
          />
          <Separator>~</Separator>
          <TimeSelect value={endTime} onChange={(selectedTime: string) => setEndTime(selectedTime)} label="종료 시간" />
        </TimeSelectContainer>
        <div>
          <label>인원</label>
          <input
            type="number"
            value={members}
            onChange={(e) => setMembers(parseInt(e.target.value, 10))}
            min={1}
            max={10}
          />
        </div>
        <Button onClick={handleReservation}>예약하기</Button>
      </ContentWrapper>
    </ReservationPageWrapper>
  );
};

export default ReservationPage;
