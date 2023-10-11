import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const StyledHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: #f0f0f0;
`;

const StyledNav = styled.nav`
  ul {
    list-style: none;
    display: flex;

    li {
      margin-left: 30px;
    }

    li:first-child {
      margin-left: 10px;
    }
  }
`;

const Header: React.FC = () => {
  return (
    <StyledHeader>
      <StyledNav>
        <ul>
          <li>
            <Link to="/Main">HOME</Link>
          </li>
          <li>
            <Link to="/Reservation">Reservation</Link>
          </li>
          <li>
            <Link to="/Notice">Notice</Link>
          </li>
          <li>
            <Link to="/Login">Login</Link>
          </li>
        </ul>
      </StyledNav>
    </StyledHeader>
  );
};

export default Header;
