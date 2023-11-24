import React, { useState, useEffect } from 'react';
import axios from '../util/axiosConfig';

type MeetingRoomType = {
  id: number;
  name: string;
};

type YourReservationType = {
  id: number;
  startTime: string;
  endTime: string;
  members: number;
  meetingRoom: MeetingRoomType;
};

const MyPage: React.FC = () => {
  const [reservations, setReservations] = useState<YourReservationType[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/reservation/check');
        setReservations(response.data as YourReservationType[]);
      } catch (error) {
        console.error('예약 정보를 가져오는 중 에러 발생:', error);
      }
    };

    fetchData();
  }, []);

  const handleLogout = async () => {
    try {
      await axios.post('http://localhost:8080/api/users/signout');
      console.log('로그아웃 성공');
    } catch (error) {
      console.error('로그아웃 실패', error);
    }
  };

  return (
    <div>
      <h2>예약 정보</h2>
      <ul>
        {reservations.map((reservation) => (
          <li key={reservation.id}>
            <p>예약 ID: {reservation.id}</p>
            <p>시작 시간: {reservation.startTime}</p>
            <p>종료 시간: {reservation.endTime}</p>
            <p>인원: {reservation.members}</p>
            <p>방 이름: {reservation.meetingRoom.name}</p>
          </li>
        ))}
      </ul>
      <button onClick={handleLogout}>로그아웃</button>
    </div>
  );
};

export default MyPage;
