import { useState } from "react";
import { register } from "../api/api";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";

export default function Signup() {
  const [form, setForm] = useState({
    username: "",
    email: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleSignup = async () => {
    try {
      await register(form);
      alert("Registered successfully");
      toast.success("Registered successfully");
      navigate("/");
    } catch {
      alert("Error");
      toast.error("Error! Try after some time");
    }
  };

  return (
    <div className="flex h-screen justify-center items-center bg-gray-100">
      <div className="bg-white p-6 rounded shadow w-80">
        <h2 className="text-xl font-bold mb-4">Signup</h2>

        <input
          className="w-full p-2 border mb-3"
          placeholder="Username"
          onChange={(e) => setForm({ ...form, username: e.target.value })}
        />

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
          onClick={handleSignup}
          className="w-full cursor-pointer bg-green-500 text-white p-2 rounded"
        >
          Register
        </button>
      </div>
    </div>
  );
}