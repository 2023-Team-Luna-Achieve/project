import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../util/axiosConfig';
import styled from 'styled-components';

// 스타일드 컴포넌트를 함수 컴포넌트 외부에서 선언
const Title = styled.h1`
  color: #333;
  font-size: 24px;
  margin-bottom: 10px;
`;

const InputField = styled.input`
  width: 100%;
  height: 40px;
  font-size: 16px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-bottom: 20px;
`;

const TextAreaField = styled.textarea`
  width: 100%;
  height: 120px;
  font-size: 16px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-bottom: 20px;
`;

const SubmitButton = styled.button`
  background-color: #232323;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
`;

const WritePage: React.FC = () => {
  const [title, setTitle] = useState('');
  const [category, setCategory] = useState('');
  const [context, setContext] = useState('');
  const navigate = useNavigate();

  const handleTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const handleCategoryChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCategory(e.target.value);
  };

  const handleContextChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContext(e.target.value);
  };

  const handleSave = () => {
    axios
      .post('/api/boards', { title, category, context })
      .then((response) => {
        console.log('글이 성공적으로 저장되었습니다!', response.data);
        alert('글이 성공적으로 등록되었습니다!');
        navigate('/Notice', { state: { title, content: context } });
      })
      .catch((error) => {
        console.error('글 저장 중 오류가 발생했습니다:', error);
        alert('글 등록 중 오류가 발생했습니다!');
      });
  };

  return (
    <div>
      <Title>제목</Title>
      <InputField type="text" value={title} onChange={handleTitleChange} placeholder="제목을 입력하세요" />
      <Title>카테고리</Title>
      <InputField type="text" value={category} onChange={handleCategoryChange} placeholder="'Notice'를 입력해주세요" />
      <Title>본문</Title>
      <TextAreaField value={context} onChange={handleContextChange} placeholder="본문을 입력하세요" />
      <SubmitButton onClick={handleSave}>입력</SubmitButton>
    </div>
  );
};

export default WritePage;
