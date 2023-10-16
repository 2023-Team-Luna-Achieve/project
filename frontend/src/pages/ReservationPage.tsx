import React from 'react';
import styled from 'styled-components';

const backgroundImageUrl = 'https://i.postimg.cc/TPRSF0MG/2023-10-15-8-26-50.png';

const StyledReservationPage = styled.div`
  position: relative;
  background-image: url(${backgroundImageUrl});
  background-size: cover;
  background-position: center;
  width: 100%;
  height: 100%;
  display: flex;
  color: #ffffff;
  overflow: hidden;
`;

const WhiteBox = styled.div`
  position: relative;
  width: 280px;
  height: 400px;
  background: #ffffff;
  box-shadow: 4.00406px 4.00406px 40.0406px rgba(0, 0, 0, 0.15);
  border-radius: 40.0406px;
  margin-top: 200px;
  margin-left: 140px;
  padding: 20px;
  margin-bottom: 110px;
`;

const TitleText = styled.div`
  font-family: 'Inter';
  font-style: normal;
  font-weight: 600;
  font-size: 28px;
  color: #000000;
  margin-top: 30px;
  margin-left: 20px;
`;

const AdditionalText = styled.div`
  font-family: 'Inter';
  font-style: normal;
  font-weight: 400;
  font-size: 18px;
  color: #000000;
  margin-top: 30px;
  margin-left: 20px;
  margin-right: 20px;
`;

const ReserveText = styled.div`
  position: absolute;
  top: 110px;
  left: 140px;
  font-size: 28px;
  font-weight: 300;
`;

const ReservationPage: React.FC = () => {
  return (
    <StyledReservationPage>
      <ReserveText>예약</ReserveText>
      <WhiteBox>
        <TitleText>Palo Alto</TitleText>
        <AdditionalText>
          실리콘밸리의 탄생지(Birthplace of Silicon Valley)로 불리는 미국 캘리포니아주 산타클라라 군에 속한 실리콘밸리
          북부의 도시의 이름에서 따온 방입니다.
        </AdditionalText>
      </WhiteBox>
    </StyledReservationPage>
  );
};

export default ReservationPage;
