import React from 'react';
import { CardElement, useStripe, useElements } from '@stripe/react-stripe-js';
import { createPayment } from '../../../../backend/purchaseService.js';

const CheckoutForm = () => {
    const stripe = useStripe();
    const elements = useElements();

    const handleSubmit = async (event) => {
        event.preventDefault();

        const { error, paymentMethod } = await stripe.createPaymentMethod({
            type: 'card',
            card: elements.getElement(CardElement),
        });

        if (!error) {
            const { id } = paymentMethod;
            const paymentRequest = { amount: 1000, id };

            createPayment(paymentRequest,
                (response) => {
                    console.log('Payment successful:', response);
                },
                (error) => {
                    console.error('Payment failed:', error);
                }
            );
        } else {
            console.error(error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <CardElement />
            <button type="submit" disabled={!stripe}>
                Pay
            </button>
        </form>
    );
};

export default CheckoutForm;
