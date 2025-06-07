import jwt from 'jsonwebtoken';

export const verifyToken = (req, res, next) => {
    const token = req.header('Authorization')?.split(' ')[1];
    //console.log('Token recibido:', token);

    if (!token) {
        return res.status(401).json({ msg: 'Access denied. No token provided.' });
    }

    try {
        const verified = jwt.verify(token, process.env.JWT_SECRET);
        console.log('Token verificado:', (verified)? true: false);
        req.user = verified;
        next();
    } catch (error) {
        console.error('Error al verificar el token:');
        res.status(400).json({ msg: 'Invalid token.' });
    }
};