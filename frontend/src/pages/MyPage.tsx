import React, { useState, useEffect } from 'react';
import axios from '../util/axiosConfig';
import styled from 'styled-components';
import Modal from '../components/Modal';

const Title = styled.h1`
  font-size: 50px;
  font-weight: bold;
  color: #3A3A3A;
  text-align: center;
  margin-top: 60px;
`;
const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
`;
const LogoutButton = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 100px;
  border: none;
  background-color: #C0C0C0;
  color: #FFFFFF;
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
const ReservationList = styled.ul`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  list-style-type: none;
  padding: 0;
`;
const ReservationItem = styled.li`
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px;
  position: relative;
`;
const DeleteButton = styled.button`
  position: absolute;
  top: 5px;
  right: 5px;
  background-color: #FF0000;
  color: #FFFFFF;
  border: none;
  padding: 5px;
  cursor: pointer;
`;
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
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState<React.ReactNode | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('https://achieve-project.store/api/reservation/check');
        setReservations(response.data as YourReservationType[]);
      } catch (error) {
        console.error('예약 정보를 가져오는 중 에러 발생:', error);
      }
    };
    fetchData();
  }, []);
  const handleLogout = async () => {
    try {
      await axios.post('https://achieve-project.store/api/users/signout');
      console.log('로그아웃 성공');
      setIsModalOpen(true);
      setModalContent('로그아웃이 완료 되었습니다.');
    } catch (error) {
      console.error('로그아웃 실패', error);
    }
  };
  const handleDeleteReservation = async (id: number) => {
    try {
      await axios.delete(`https://achieve-project.store/api/reservation/${id}`);
      setReservations((prevReservations) => prevReservations.filter((reservation) => reservation.id !== id));
      console.log('예약 삭제 성공');
      setIsModalOpen(true);
      setModalContent('예약이 취소 되었습니다.');
    } catch (error) {
      console.error('예약 삭제 실패', error);
    }
  };
  return (
    <div>
      <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
        {modalContent}
      </Modal>
      <Title>예약 정보</Title>
      <ReservationList>
        {reservations.map((reservation) => (
          <ReservationItem key={reservation.id}>
            <br />
            <p>시작 시간 : {reservation.startTime}</p>
            <p>종료 시간 : {reservation.endTime}</p>
            <p>인원 : {reservation.members}명</p>
            <p>방 이름 : {reservation.meetingRoom.name}</p>
            <DeleteButton onClick={() => handleDeleteReservation(reservation.id)}>취소</DeleteButton>
          </ReservationItem>
        ))}
      </ReservationList>
      <ButtonContainer>
        <LogoutButton onClick={handleLogout}>로그아웃</LogoutButton>
      </ButtonContainer>
    </div>
  );
};
export default MyPage;