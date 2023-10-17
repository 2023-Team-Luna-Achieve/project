import React from 'react';
import styled from 'styled-components';

const ReservationPageWrapper = styled.div`
  display: flex;
  align-items: center;
`;

const ContentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 20px;
`;

const Title = styled.h1`
  font-family: 'Inter';
  font-style: normal;
  font-weight: 600;
  font-size: 24px;
  line-height: 29px;
  color: #000000;
  margin-left: 40px;
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
  margin-left: 200px;
`;

const ProfileImageContainer = styled.div`
  width: 100px;
  height: 100px;
  overflow: hidden;
`;

const ProfileImage = styled.div`
  width: 100px;
  height: 100px;
  background: #ffffff;
  border: 1px solid #cccccc;
  border-radius: 50%;
`;

const ReservationPage: React.FC = () => {
  return (
    <ReservationPageWrapper>
      <ProfileImageContainer>
        <ProfileImage />
      </ProfileImageContainer>
      <ContentWrapper>
        <Title>Palo Alto</Title>
        <LoremText>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Quo, delectus! Sunt corrupti repellat iste amet
          laboriosam non dolorum iusto laudantium quaerat alias. Quasi odio tenetur porro, nihil nulla facere accusamus
          quibusdam ea voluptatem suscipit!
        </LoremText>
      </ContentWrapper>
    </ReservationPageWrapper>
  );
};

export default ReservationPage;
