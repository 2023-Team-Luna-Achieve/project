import { atom } from 'recoil';

// 로컬 스토리지에서 accessToken을 가져오기
const getAccessTokenFromLocalStorage = () => {
  return localStorage.getItem('accessToken');
};

// 로컬 스토리지에서 토큰을 가져와 초기 상태 설정
const initialAccessToken = getAccessTokenFromLocalStorage();
const initialIsLoggedIn = initialAccessToken ? true : false;

// atom 정의
export const isLoggedInState = atom({
  key: 'isLoggedInState',
  default: initialIsLoggedIn,
});

export const accessTokenState = atom({
  key: 'accessTokenState',
  default: initialAccessToken,
});

export const refreshTokenState = atom({
  key: 'refreshTokenState',
  default: undefined,
});
