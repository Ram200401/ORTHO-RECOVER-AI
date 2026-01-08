### Ortho Recover AI API Examples

#### 1. Registration
**POST** `/api/auth/register.php`
```json
{
    "name": "Dr. Smith",
    "email": "smith@physio.com",
    "password": "password123",
    "role": "physiotherapist",
    "qualification": "BPT, MPT",
    "specialization": "Sports Orthopedics"
}
```
**Response (201):**
```json
{
    "success": true,
    "message": "User registered successfully",
    "data": { "id": 1 }
}
```

#### 2. Login
**POST** `/api/auth/login.php`
```json
{
    "email": "smith@physio.com",
    "password": "password123"
}
```
**Response (200):**
```json
{
    "success": true,
    "message": "Login successful",
    "data": {
        "token": "eyJ0eXAi...",
        "user": { "id": 1, "name": "Dr. Smith", "role": "physiotherapist" }
    }
}
```

#### 3. Add Patient (Physio)
**POST** `/api/physio/add-patient.php`
```json
{
    "patient_email": "patient@example.com"
}
```

#### 4. Upload Swelling (Patient)
**POST** `/api/patient/swelling-upload.php`
*Multipart/form-data*
- `image`: [binary file]

**Response (201):**
```json
{
    "success": true,
    "message": "Image uploaded successfully",
    "data": { "file": "1736284200_photo.jpg" }
}
```
