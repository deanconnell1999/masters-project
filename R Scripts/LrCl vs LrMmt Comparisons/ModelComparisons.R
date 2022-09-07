library(tidyverse)
library(dplyr)
library(ggplot2)

lr_cl <- read.csv("random_lr_cl_stats.csv")
lr_mmt <- read.csv("random_lr_mmt_stats.csv")

# mean metrics by parameter function
select_metrics <- function(dataset){
  dataset %>% select(workload, numberOfMigrations, energy, sla) %>% mutate(ee = 1/(energy*sla))
}

thr_rs_sel <- data.frame(c(select_metrics(thr_rs),list(Method = rep("ThrRs", 50))))
thr_mmt_sel <- data.frame(c(select_metrics(thr_mmt),list(Method = rep("ThrMmt", 50))))
lr_mmt_sel <- data.frame(c(select_metrics(lr_mmt),list(Method = rep("LrMmt", 50))))
lrr_mmt_sel <- data.frame(c(select_metrics(lrr_mmt),list(Method = rep("LrrMmt", 50))))

comparison <- rbind(thr_rs_sel,thr_mmt_sel,lr_mmt_sel,lrr_mmt_sel)

# Energy Consumption
ggplot(comparison, aes(x=workload, y=energy, color=Method)) + 
  geom_line(size=1.15)+
  scale_x_continuous(limits=c(1, 50),
                     breaks=seq(5, 50, by=5),
                     labels=as.character(seq(5, 50, by=5)),
                     name="Workload") +
  scale_y_continuous(limits=c(240, max(comparison$energy)+20),
                     breaks=seq(240, max(comparison$energy)+20, by=20),
                     labels=as.character(seq(240, max(comparison$energy)+20, by=20)),
                     name="Energy Consumption") +
  theme(
    axis.line.y = element_blank(),
    axis.ticks.y = element_blank(),
    axis.ticks.x = element_blank(),
    #axis.title.x = element_blank(),
    axis.line.x = element_blank(),
    axis.title.y = element_blank(),
    panel.background = element_blank(),
    panel.grid.major.y = element_line(size=0.4, linetype="solid", color="grey80"),
    panel.grid.major.x = element_blank())


#VM Migrations
ggplot(comparison, aes(x=workload, y=numberOfMigrations, color=Method)) + 
  geom_line(size=1.15)+
  scale_x_continuous(limits=c(1, 50),
                     breaks=seq(5, 50, by=5),
                     labels=as.character(seq(5, 50, by=5)),
                     name="Workload") +
  scale_y_continuous(limits=c(20000, max(comparison$numberOfMigrations)+1000),
                     breaks=seq(20000, max(comparison$numberOfMigrations)+1000, by=1000),
                     labels=as.character(seq(20000, max(comparison$numberOfMigrations)+1000, by=1000)),
                     name="Number of Migrations") +
  theme(
    axis.line.y = element_blank(),
    axis.ticks.y = element_blank(),
    axis.ticks.x = element_blank(),
    #axis.title.x = element_blank(),
    axis.line.x = element_blank(),
    axis.title.y = element_blank(),
    panel.background = element_blank(),
    panel.grid.major.y = element_line(size=0.4, linetype="solid", color="grey80"),
    panel.grid.major.x = element_blank())


#SLAVs
ggplot(comparison, aes(x=workload, y=sla, color=Method)) + 
  geom_line(size=1.15)+
  scale_x_continuous(limits=c(1, 50),
                     breaks=seq(5, 50, by=5),
                     labels=as.character(seq(5, 50, by=5)),
                     name="Workload") +
  scale_y_continuous(limits=c(0.00007, max(comparison$sla)+0.00001),
                     breaks=seq(0.00007, max(comparison$sla)+0.00001, by=0.00001),
                     labels=as.character(seq(0.00007, max(comparison$sla)+0.00001, by=0.00001)),
                     name="SLAVs") +
  theme(
    axis.line.y = element_blank(),
    axis.ticks.y = element_blank(),
    axis.ticks.x = element_blank(),
    #axis.title.x = element_blank(),
    axis.line.x = element_blank(),
    axis.title.y = element_blank(),
    panel.background = element_blank(),
    panel.grid.major.y = element_line(size=0.4, linetype="solid", color="grey80"),
    panel.grid.major.x = element_blank())
