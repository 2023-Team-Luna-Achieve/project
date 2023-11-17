import React, { useState } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import { AxiosError } from 'axios';

const FormContainer = styled.div`
  max-width: 600px;
  margin: 200px auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  max-height: calc(100vh - 40px);
  overflow: hidden;
`;

const StyledForm = styled.form``;

const FormGroup = styled.div``;

const Select = styled.select``;

const Input = styled.input``;

const JoinButton = styled.button`
  background-color: #c0c0c0;
  color: #ffffff;
  font-size: 16px;
  border-radius: 5px;
  transition:
    background-color 0.3s,
    border-color 0.3s,
    color 0.3s;

  &:hover {
    background-color: #000000;
    border-color: #000000;
  }
`;

const SendCodeButton = styled(JoinButton)`
  background-color: #008cba;
  border: 2px solid #008cba;
  font-size: 15px;

  &:hover {
    background-color: #005f6b;
    border-color: #005f6b;
  }
`;

const ConfirmAuthButton = styled(SendCodeButton)``;

const EmailInput = styled(Input).attrs({ type: 'email', autoComplete: 'email' })``;

const AuthCodeInput = styled(Input).attrs({ autoComplete: 'off' })``;

const PasswordInput = styled(Input).attrs({ type: 'password', autoComplete: 'new-password' })``;

const JoinPage: React.FC = () => {
  const [affiliation, setAffiliation] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [authCode, setAuthCode] = useState('');
  const [name, setName] = useState('');
  const [verificationMessage, setVerificationMessage] = useState<string>('');

  const sendCode = async () => {
    try {
      await axios.post('http://localhost:8080/api/email/verification/request', {
        email: email,
      });

      console.log(`코드가 성공적으로 전송되었습니다: ${email}`);
    } catch (error) {
      if (axios.isAxiosError(error)) {
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

    console.log('가입 정보:', {
      affiliation,
      name,
      email,
      authCode,
      password,
      passwordConfirm,
    });
  };

  return (
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
            <option value="option1">Techeer</option>
            <option value="option2">Techeer Partners</option>
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
          <JoinButton type="submit">{'가입하기'}</JoinButton>
        </div>
      </StyledForm>
    </FormContainer>
  );
};

export default JoinPage;
