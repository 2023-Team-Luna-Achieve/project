import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import axios from '../util/axiosConfig';

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
  width: 300px;
  height: 180px;
  background-color: #ffffff;
  border: 1px solid #e4e4e4;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 10px;
  margin-bottom: 20px;
`;

const StyledLink = styled(Link)`
  text-decoration: none;
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

const NoticePage: React.FC<NoticeProps> = ({}) => {
  const [data, setData] = useState<any[]>([]);
  const [page, setPage] = useState<number>(1);
  const [loading, setLoading] = useState<boolean>(false);
  const navigate = useNavigate();

  useEffect(() => {
    fetchData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [page]);

  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/board/notice?page=${page}&limit=10`);
      setData([...data, ...response.data]);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching data:', error);
      setLoading(false);
    }
  };

  const handleWriteClick = () => {
    navigate('/WritePage');
  };

  const loadMore = () => {
    setPage(page + 1);
  };

  return (
    <>
      <Button onClick={handleWriteClick}>글쓰기</Button>
      <PageContainer>
        {data &&
          data.map((item: any, index: number) => (
            <StyledLink to={`/NewPage/${item.id}`} key={index}>
              <Box>
                <Title>{item.title}</Title>
                <Content>{item.context}</Content>
              </Box>
            </StyledLink>
          ))}
        {loading ? <p>Loading...</p> : <Button onClick={loadMore}>더 불러오기</Button>}
      </PageContainer>
    </>
  );
};

export default NoticePage;
