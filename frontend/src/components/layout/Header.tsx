import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const StyledHeaderBorder = styled.div`
  border-bottom: 1px solid #dddddd;
`;

const StyledHeader = styled.div`
  display: flex;
  justify-content: center; /* 중앙 정렬 */
  align-items: center;
  background-color: #ffffff;
  font-size: 20px;
  width: 100%; /* 헤더의 전체 너비를 사용하도록 설정 */
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
            <li>
              <Link to="/Login">Login</Link>
            </li>
          </ul>
        </StyledNav>
      </StyledHeader>
    </StyledHeaderBorder>
  );
};

export default Header;
