import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import axios from 'axios';

type NoticeProps = {
  title: string;
  context: string;
};

const PageContainer = styled.div`
  background-color: #ececec;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  max-width: 100%;
  margin: 0 auto;
  padding: 20px;
`;

const Box = styled.div`
  width: calc(20%);
  height: 180px;
  background-color: #ffffff;
  border: 1px solid #e4e4e4;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 10px;
`;

const Title = styled.div`
  font-weight: bold;
  color: #000000;
  font-size: 18px;
  margin-bottom: 10px;
  text-align: left;
  margin-top: 10px;
  margin-left: 10px;
`;

const Content = styled.div`
  color: #575757;
  font-size: 14px;
  text-align: left;
  margin-left: 10px;
  margin-right: 10px;
  margin-bottom: 10px;
`;

const Button = styled.button`
  width: 100px;
  height: 40px;
  border-radius: 4px;
  border: 1px solid #232323;
  background-color: #232323;
  color: white;
  font-size: 16px;
  cursor: pointer;
`;

const NoticePage: React.FC<NoticeProps> = ({ title, context }) => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/board/notice');
        setData(response.data);
        console.log(response.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  return (
    <>
      <Link to="/WritePage">
        <Button>글 작성</Button>
      </Link>
      <PageContainer>
        {data &&
          data.map((item: any, index: number) => (
            <Box key={index}>
              <Title>{item.title}</Title>
              <Content>{item.context}</Content>
            </Box>
          ))}
        <Box>
          <Title>{title}</Title>
          <Content>{context}</Content>
        </Box>
      </PageContainer>
    </>
  );
};

export default NoticePage;
