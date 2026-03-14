# ⚡ WorkSure – Online Skilled Workers Finder System

---

## 📋 Overview

WorkSure is a full-stack web-based platform designed to bridge the gap between clients and verified skilled professionals. The system facilitates seamless connections between individuals/businesses needing services and qualified technicians, electricians, plumbers, and other skilled workers, enhanced with an intelligent AI chatbot for real-time assistance.

---

## ✨ Key Features

### 🤖 **AI-Powered Chatbot**
- **24/7 Customer Support**

WorkSure now includes a **chatbot** to assist users with FAQs, service guidance, and general support. The chatbot can be **AI-powered** (using OpenAI API) or a simple rule-based bot.

### 💻 How It Works

- Users can ask questions related to hiring workers, payments, or account management.
- The bot responds with helpful answers in real-time.
- AI chatbot version uses **OpenAI API** to generate intelligent responses.

### ⚙️ Setup

1. Add your OpenAI API key in `application.properties`:

```text
spring.ai.openai.api-key=YOUR_OPENAI_API_KEY_HERE

```
### 🔐 **Authentication & Security**
- **Role-Based Access Control (RBAC)** with three distinct user types:
  - **Admin**: Full system control and monitoring
  - **Client**: Service request, hiring, and payment management
  - **Skilled Worker**: Profile management and job handling
- **JWT-based authentication** for secure API access
- Protected routes and endpoints based on user roles

### 👥 **For Clients**
- 🔍 **Search & Filter**: Find skilled workers by location, profession, rating, and availability
- 📝 **Post Service Requests**: Describe specific needs and get matched with suitable workers
- 💳 **Secure Online Payments**: Integrated payment system for hassle-free transactions
- 📊 **Track Job Status**: Real-time updates on job progress from hiring to completion
- ⭐ **Rating & Feedback**: Review and rate workers after service completion
- 💬 **Chatbot Assistance**: Get instant help with platform navigation and service selection

### 🔧 **For Skilled Workers**
- ✅ **Verification Workflow**: Get verified through platform's validation process
- 📋 **Job Management**: Accept/decline jobs, update job status, manage schedule
- 📈 **Profile Building**: Showcase skills, experience, certifications, and portfolio
- 💬 **Direct Communication**: Chat with clients for job details
- 🏆 **Reputation Building**: Earn ratings and reviews to boost credibility
- 🤖 **Chatbot Support**: Quick answers about platform policies and job opportunities

### ⚙️ **For Administrators**
- 👁️ **Monitoring Dashboard**: Track all platform activities and transactions
- ✅ **Worker Verification**: Manage verification requests and approve qualified workers
- 📊 **Analytics & Reports**: Gain insights into platform usage and performance
- 🔧 **Content Management**: Manage categories, service types, and platform rules
- 🤖 **Chatbot Management**: Monitor chatbot performance, update knowledge base, view chat logs

### 🔔 **Additional Features**
- **Responsive Design**: Mobile-friendly interface for on-the-go access
- **Clean Architecture**: Well-structured codebase following best practices

---

## 🛠️ Technology Stack

### **Frontend**
- **React** - UI library for building interactive interfaces
- **Tailwind CSS** - Utility-first CSS framework for styling
- **React Router** - Client-side routing


### **Backend**
- **Spring Boot** - Java framework for RESTful APIs
- **Java** - Primary backend programming language
- **Spring Security** - Authentication and authorization
- **JWT** - JSON Web Tokens for secure authentication

### **Database**
- **MySQL** - Relational database management system


### **DevOps & Tools**
- **Git & GitHub** - Version control and collaboration
- **Postman** - API testing and documentation
- **Figma** - UI/UX design and prototyping

---

## 📦 Installation & Setup

```bash
# Clone frontend repository
git clone https://github.com/HasalKenula/WorkSure-frontend.git
cd WorkSure-frontend
# Install dependencies
npm install
npm run dev

# Clone backend repository
git clone https://github.com/HasalKenula/WorkSure-backend.git
cd WorkSure-backend
# Open in IntelliJ IDEA / Eclipse
# Configure MySQL database credentials
# Run Spring Boot application

```
---