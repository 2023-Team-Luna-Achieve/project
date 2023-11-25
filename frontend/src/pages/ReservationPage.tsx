import React, { useState } from 'react';
import Calendar from 'react-calendar'; // 예약 페이지에서 관리
import 'react-calendar/dist/Calendar.css';
import styled from 'styled-components';
import TimeSelect from '../components/TimeSelect';
import axios from '../util/axiosConfig';

const ReservationPageWrapper = styled.div``;

const ContentWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

const HeaderSection = styled.div``;

const Title = styled.h1`
  font-size: 50px;
  font-weight: bold;
  color: #3a3a3a;
  text-align: center;
  margin-top: 60px;
  margin-bottom: 0px;
`;

const CalendarSelectContainer = styled.div`
  margin-top: 50px;
`;

const MemberSelect = styled.div``;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 20px; /* 버튼과 상단 간격 조절 */
`;

const TimeSelectContainer = styled.div`
  display: flex;
  flex-direction: column; /* 수직으로 정렬하도록 추가 */
  justify-content: space-between;
  align-items: center;
  width: 25%;
  margin-top: 20px;
`;

const Button = styled.button`
  border: none;
  background-color: #c0c0c0;
  color: #ffffff;
  font-size: 16px;
  height: 26px;
  width: 49%;
  transition:
    background-color 0.3s,
    color 0.3s;
  &:hover {
    background-color: #000000;
  }
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
      <HeaderSection>
        <Title>Palo Alto 예약하기</Title>
      </HeaderSection>
      <ContentWrapper>
        <CalendarSelectContainer>
          <Calendar
            onChange={handleDateChange}
            value={selectedDate}
            formatDay={(_, date) => (date instanceof Date ? date.getDate().toString() : '')}
          />
        </CalendarSelectContainer>
        <TimeSelectContainer>
          <TimeSelect
            value={startTime}
            onChange={(selectedTime: string) => setStartTime(selectedTime)}
            label="예약 시작 시간을 선택하세요."
          />
          <TimeSelect
            value={endTime}
            onChange={(selectedTime: string) => setEndTime(selectedTime)}
            label="예약 종료 시간을 선택하세요."
          />
          <MemberSelect>
            <label>인원을 선택하세요. (최대 10명)</label>
            <input
              type="number"
              value={members}
              onChange={(e) => setMembers(parseInt(e.target.value, 10))}
              min={1}
              max={10}
            />
          </MemberSelect>
        </TimeSelectContainer>
      </ContentWrapper>
      <ButtonContainer>
        <Button onClick={handleReservation}>예약하기</Button>
      </ButtonContainer>
    </ReservationPageWrapper>
  );
};

export default ReservationPage;
