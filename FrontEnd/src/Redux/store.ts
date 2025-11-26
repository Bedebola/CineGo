import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./authSlice";
import filmesReducer from "./filmesSlice";

export const store = configureStore({
  reducer: {
    auth: authReducer,
    filmes: filmesReducer
  }
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
