import { useState } from "react";
import { login } from "../api/api";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";

export default function Login() {
  const [form, setForm] = useState({ email: "", password: "" });
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await login(form);
      localStorage.setItem("token", res.data);
      toast.success("Logged in successfully");
      navigate("/home");
    } catch {
      alert("Invalid credentials");
      toast.error("Invalid credentials"); 
    }
  };

  return (
    <div className="flex h-screen justify-center items-center bg-gray-100">
      <div className="bg-white p-6 rounded shadow w-80">
        <h2 className="text-xl font-bold mb-4">Login</h2>

        <input
          className="w-full p-2 border mb-3"
          placeholder="Email"
          onChange={(e) => setForm({ ...form, email: e.target.value })}
        />

        <input
          type="password"
          className="w-full p-2 border mb-3"
          placeholder="Password"
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />

        <button
          onClick={handleLogin}
          className="w-full cursor-pointer bg-blue-500 text-white p-2 rounded"
        >
          Login
        </button>

        <p
          className="mt-3 text-sm text-blue-600 cursor-pointer"
          onClick={() => navigate("/signup")}
        >
          Create account
        </p>
      </div>
    </div>
  );
}