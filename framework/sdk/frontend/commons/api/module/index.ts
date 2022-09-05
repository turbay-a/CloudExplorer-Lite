import { get } from "@commons/request";
import type { Module } from "./type";
import type Result from "@commons/request/Result";
import type { Ref } from "vue";

export const listModules = (loading?: Ref<boolean>) => {
  const modules: Promise<Result<Array<Module>>> = get(
    "/api/modules",
    {},
    loading
  );
  return modules;
};

/**
 *获取所有正在运行的模块
 * @returns
 */
export const listRuningModules = (loading?: Ref<boolean>) => {
  const modules: Promise<Result<Array<Module>>> = get(
    "/api/list/modules",
    {},
    loading
  );
  return modules;
};

/**
 * 获取当前模块
 */
export const getCurrentModule = () => {
  const currentModule: Promise<Result<Module>> = get("/api/getCurrentModule", {
    moduleName: import.meta.env.VITE_APP_NAME, //todo 这里其实不需要，url中basePath有名字了
  });
  return currentModule;
};
export type { Module };