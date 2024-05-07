import React, { useEffect, ReactNode } from 'react';
import styled from 'styled-components';
import ReactModal from 'react-modal';

const StyledModal = styled(ReactModal)`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  max-width: 400px;
  width: 100%;
`;

interface ModalProps {
  children: ReactNode;
  isOpen: boolean;
  onClose: () => void;
}

const Modal: React.FC<ModalProps> = ({ children, isOpen, onClose }) => {
  useEffect(() => {
    ReactModal.setAppElement('#root');
  }, []);

  return (
    <StyledModal isOpen={isOpen} onRequestClose={onClose} shouldCloseOnOverlayClick shouldCloseOnEsc>
      {children}
    </StyledModal>
  );
};

export default Modal;
