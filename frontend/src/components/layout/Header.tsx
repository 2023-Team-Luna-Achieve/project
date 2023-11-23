import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import axios from 'axios';

const StyledHeaderBorder = styled.div`
  border-bottom: 1px solid #dddddd;
`;
const StyledHeader = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #ffffff;
  font-size: 20px;
  width: 100%;
`;
const StyledNav = styled.nav`
  ul {
    list-style: none;
    display: flex;
    justify-content: space-around;
    padding: 0;
    li {
      margin: 0 30px;
      a {
        color: black;
        text-decoration: none;
      }
    }
  }
`;
const Header = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  useEffect(() => {
    axios
      .get('http://localhost:8080/api/users/login-confirm')
      .then((response) => {
        if (response.data.loggedIn) {
          setIsLoggedIn(true);
        } else {
          setIsLoggedIn(false);
        }
      })
      .catch((error) => {
        console.error('로그인 상태 확인 중 에러:', error);
        setIsLoggedIn(false);
      });
  }, [isLoggedIn]);
  return (
    <StyledHeaderBorder>
      <StyledHeader>
        <StyledNav>
          <ul>
            <li>
              <Link to="/Main">Home</Link>
            </li>
            <li>
              <Link to="/About">About</Link>
            </li>
            <li>
              <Link to="/Select">Reservation</Link>
            </li>
            <li>
              <Link to="/Notice">Notice</Link>
            </li>
            <li>
              <Link to="/Community">Community</Link>
            </li>
            {isLoggedIn ? (
              <li>
                <Link to="/Mypage">Mypage</Link>
              </li>
            ) : (
              <>
                <li>
                  <Link to="/Login">Login</Link>
                </li>
                <li>
                  <Link to="/Join">Join</Link>
                </li>
              </>
            )}
          </ul>
        </StyledNav>
      </StyledHeader>
    </StyledHeaderBorder>
  );
};
export default Header;
