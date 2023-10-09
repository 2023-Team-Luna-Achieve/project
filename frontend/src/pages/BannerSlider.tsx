import React from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import styled from 'styled-components';

const BannerSlider: React.FC = () => {
  const settings = {
    className: 'center',
    centerMode: true,
    centerPadding: '60px',
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  const Banner = styled.div`
    width: 100%;
    height: 40%;
    overflow: visible;
  `;

  const Background = styled.div`
    background-color: #ffeffa;
    width: 100%;
    height: 100%;
    margin-left: 100px;
    margin-right: 100px;
  `;

  const StyledSlider = styled(Slider)`
    height: 100%;
  `;

  return (
    <Banner>
      <StyledSlider {...settings}>
        <Background>
          <p>안녕</p>
        </Background>
        <div>
          <img src="https://placekitten.com/1696/565" alt="Banner 2" style={{ width: '100%', height: 'auto' }} />
        </div>
        <div>
          <img src="https://placekitten.com/1696/565" alt="Banner 3" style={{ width: '100%', height: 'auto' }} />
        </div>
      </StyledSlider>
    </Banner>
  );
};

export default BannerSlider;
