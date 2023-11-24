import React, { useState } from 'react';
import styled from 'styled-components';
import axios from '../util/axiosConfig';
import { AxiosError } from 'axios';
import { Link } from 'react-router-dom';

const JoinText = styled.div`
  font-size: 70px;
  font-weight: bold;
  color: #3a3a3a;
  text-align: center;
  margin-top: 60px;
  margin-bottom: 0px;
`;
const LoginText = styled.div`
  font-size: 20px;
  font-weight: 300;
  color: #585858;
  text-align: center;
`;
const LoginLink = styled(Link)`
  font-size: 20px;
  font-weight: 300;
  color: #ffe8f6;
  text-align: center;
  text-decoration: none;
  cursor: pointer;
  &:hover {
    color: #ffe8f6;
  }
`;
const FormContainer = styled.div`
  max-width: 600px;
  margin: 50px auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  max-height: calc(100vh - 40px);
  overflow: hidden;
  color: #585858;
`;
const StyledForm = styled.form``;
const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
`;
const Select = styled.select`
  border-radius: 0;
  border: 0.7px solid #c0c0c0;
  color: #585858;
  height: 30px;
  width: 405px;
`;
const Input = styled.input`
  border-radius: 0;
  border: 0.7px solid #c0c0c0;
  height: 26px;
  width: 400px;
`;
const JoinButton = styled.button`
  border: none;
  background-color: #c0c0c0;
  color: #ffffff;
  font-size: 16px;
  height: 26px;
  width: 100%;
  transition:
    background-color 0.3s,
    color 0.3s;
  &:hover {
    background-color: #000000;
  }
`;
const SendCodeButton = styled(JoinButton)`
  border: none;
  background-color: #c0c0c0;
  font-size: 15px;
  height: 24px;
  &:hover {
    background-color: black;
    border-color: black;
  }
`;
const ConfirmAuthButton = styled(SendCodeButton)`
  border: none;
  height: 24px;
`;
const EmailInput = styled(Input).attrs({ type: 'email', autoComplete: 'email' })`
  margin-bottom: 10px;
  border-radius: 0;
`;
const AuthCodeInput = styled(Input).attrs({ autoComplete: 'off' })`
  margin-bottom: 10px;
  border-radius: 0;
`;
const PasswordInput = styled(Input).attrs({ type: 'password', autoComplete: 'new-password' })`
  margin-bottom: 0px;
  border-radius: 0;
`;

const JoinPage: React.FC = () => {
  const [affiliation, setAffiliation] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [authCode, setAuthCode] = useState('');
  const [name, setName] = useState('');
  const [verificationMessage, setVerificationMessage] = useState<string>('');
  const [isPasswordMatch] = useState(false);

  const sendCode = async () => {
    try {
      await axios.post('http://localhost:8080/api/email/verification/request', {
        email: email,
      });

      console.log(`코드가 성공적으로 전송되었습니다: ${email}`);
    } catch (error) {
      if ((error as AxiosError).isAxiosError) {
        // Axios 에러인 경우
        console.error('코드 전송 중 에러:', (error as AxiosError).message);
      } else {
        // 그 외의 에러인 경우
        console.error('코드 전송 중 에러:', error);
      }
    }
  };

  const handleSendCodeClick: React.MouseEventHandler<HTMLButtonElement> = async () => {
    try {
      // SendCodeButton이 클릭될 때 email 값을 설정하고 sendCode 함수 호출
      await sendCode();
    } catch (error) {
      console.error('handleSendCodeClick 오류:', (error as AxiosError).message);
    }
  };

  const handleConfirmAuthClick = async () => {
    try {
      // 클라이언트에서 서버로 코드 확인 요청을 보냄
      const response = await axios.post('http://localhost:8080/api/email/verification/confirm', {
        email,
        code: authCode,
      });

      setVerificationMessage(response.data.message);
    } catch (error) {
      console.error('인증 확인 중 에러:', (error as AxiosError).message);
    }
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    console.log('양식 제출 중...');

    // 이메일이 검증되었고 비밀번호가 일치하는지 확인
    {
      console.log('가입 정보:', {
        affiliation,
        name,
        email,
        password,
      });

      try {
        console.log('axios.post 이전');
        const response = await axios.post('http://localhost:8080/api/users/signup', {
          affiliation,
          name,
          email,
          password,
        });

        console.log('서버 응답:', response.data);
      } catch (error) {
        console.error('서버로의 데이터 전송 중 에러:', error);
      }
    }
    if (!isPasswordMatch) {
      console.error('비밀번호가 일치하지 않습니다.');
    }
  };

  return (
    <>
      <JoinText className="Gaib">가입하기</JoinText>
      <LoginText className="imi">
        이미 계정이 있습니까?{' '}
        <LoginLink to="/login" className="login">
          로그인
        </LoginLink>
        하기
      </LoginText>
      <FormContainer>
        <StyledForm onSubmit={handleSubmit}>
          <FormGroup>
            <label htmlFor="affiliation">소속</label>
            <Select
              id="affiliation"
              name="affiliation"
              value={affiliation}
              onChange={(event) => setAffiliation(event.target.value)}
            >
              <option value="">선택하세요</option>
              <option value="Techeer">Techeer</option>
              <option value="TecheerPartners">Techeer Partners</option>
            </Select>
          </FormGroup>
          <FormGroup>
            <label htmlFor="name">이름</label>
            <Input type="text" id="name" name="name" value={name} onChange={(event) => setName(event.target.value)} />
          </FormGroup>
          <FormGroup>
            <label htmlFor="email">이메일</label>
            <EmailInput id="email" name="email" value={email} onChange={(event) => setEmail(event.target.value)} />
            <SendCodeButton onClick={handleSendCodeClick}>{'인증'}</SendCodeButton>
          </FormGroup>
          <FormGroup>
            <label htmlFor="authCode">인증코드</label>
            <AuthCodeInput
              id="authCode"
              name="authCode"
              value={authCode}
              onChange={(event) => setAuthCode(event.target.value)}
            />
            <ConfirmAuthButton onClick={handleConfirmAuthClick}>{'인증확인'}</ConfirmAuthButton>
            <div>{verificationMessage}</div>
          </FormGroup>
          <FormGroup>
            <label htmlFor="password">비밀번호</label>
            <PasswordInput
              id="password"
              name="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
            />
          </FormGroup>
          <FormGroup>
            <label htmlFor="passwordConfirm">비밀번호 확인</label>
            <PasswordInput
              id="passwordConfirm"
              name="passwordConfirm"
              value={passwordConfirm}
              onChange={(event) => setPasswordConfirm(event.target.value)}
            />
          </FormGroup>
          <div style={{ display: 'flex', justifyContent: 'flex-end', width: '100%' }}>
            <JoinButton type="submit" onClick={() => console.log('가입하기 버튼이 클릭되었습니다.')}>
              {'가입하기'}
            </JoinButton>
          </div>
        </StyledForm>
      </FormContainer>
    </>
  );
};

export default JoinPage;
