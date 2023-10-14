import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const StyledHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: #f6f6f6;
`;

const StyledNav = styled.nav`
  ul {
    list-style: none;
    display: flex;
    justify-content: space-around; /* Adjust the justify-content property */
    padding: 0;

    li {
      margin: 0 10px;

      a {
        color: black;
        text-decoration: none;
      }
    }
  }
`;

// Example usage in a component
const Header = () => {
  return (
    <StyledHeader>
      <StyledNav>
        <ul>
          <li>
            <Link to="/Main">Home</Link>
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
  );
};

export default Header;
