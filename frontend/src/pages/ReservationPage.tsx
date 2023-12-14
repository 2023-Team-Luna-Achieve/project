import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import styled from 'styled-components';
import TimeSelect from '../components/TimeSelect';
import axios from '../util/axiosConfig';
import Select from 'react-select';
import { useNavigate } from 'react-router-dom';

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

const MemberSelect = styled.div`
  margin-top: 10px;
`;

const Notice = styled.div`
  margin-top: 15px;
  color: #7b7b7b;
  font-size: 14px;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 30px;
`;

const TimeSelectContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  width: 20%;
  margin-top: 40px;
`;

const Button = styled.button`
  border: none;
  background-color: #c0c0c0;
  color: #ffffff;
  font-size: 20px;
  height: 40px;
  width: 10%;
  transition:
    background-color 0.3s,
    color 0.3s;
  &:hover {
    background-color: #000000;
  }
`;

const ReservationPage: React.FC = () => {
  const navigate = useNavigate(); // useNavigate를 사용하여 navigate 변수 설정
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const [selectedDate, setSelectedDate] = useState<Date | null>(null); // 초기 상태를 Date | null로 수정
  const [startTime, setStartTime] = useState<string>('');
  const [endTime, setEndTime] = useState<string>('');
  const [selectedMembers, setSelectedMembers] = useState<{ value: number; label: string } | null>({
    value: 0,
    label: '0명',
  });

  const membersOptions = Array.from({ length: 10 }, (_, i) => ({ value: i + 1, label: `${i + 1}명` }));

  const handleDateChange = (date: Date | Date[] | null) => {
    setSelectedDate(date as Date | null);
  };

  const handleReservation = async () => {
    console.log('members:', selectedMembers?.value);

    if (selectedDate && startTime && endTime && selectedMembers?.value !== undefined) {
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
          members: selectedMembers.value,
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

  useEffect(() => {
    axios
      .get('http://localhost:8080/api/users/login-confirm')
      .then((response) => {
        setIsLoggedIn(response.data.loggedIn);
      })
      .catch((error) => {
        console.error('로그인 상태 확인 중 에러:', error);
        setIsLoggedIn(false);
      });
  }, []);

  useEffect(() => {
    // 로그인 상태를 확인하고 리디렉션
    if (!isLoggedIn) {
      navigate('/Login'); // 로그인 페이지로 리디렉션
    }
  }, [isLoggedIn, navigate]);

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
          <Notice>* 캘린더에서 날짜를 먼저 선택해주세요.</Notice>
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
            <label>인원을 선택하세요.</label>
            <Select
              options={membersOptions}
              value={selectedMembers}
              onChange={(selectedOption) => setSelectedMembers(selectedOption)}
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
