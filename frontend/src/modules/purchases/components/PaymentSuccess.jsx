import React from 'react';
import { useNavigate } from 'react-router-dom';

const PaymentSuccess = () => {
    const navigate = useNavigate();

    const handleContinue = () => {
        navigate('/shopping/purchase-completed');
    };

    return (
        <div className="justify-content-center text-center">
            <h1>El pago se ha realizado con éxito</h1>
            <button onClick={handleContinue} className="btn btn-primary">
                Ver detalles de la compra
            </button>
        </div>
    );
};

export default PaymentSuccess;
